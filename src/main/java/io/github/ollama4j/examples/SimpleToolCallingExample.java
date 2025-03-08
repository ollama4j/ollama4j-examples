package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.ToolFunction;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class SimpleToolCallingExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");

        askModel(host, Utilities.getFromConfig("tools_model_mistral"));
        askModel(host,  Utilities.getFromConfig("tools_model_qwen_2_5_7b"));
    }

    public static void askModel(String ollamaHost, String modelName) throws ToolInvocationException, OllamaBaseException, IOException, InterruptedException {
        OllamaAPI ollamaAPI = new OllamaAPI(ollamaHost);
        ollamaAPI.setVerbose(true);
        ollamaAPI.setRequestTimeoutSeconds(60);

        Tools.ToolSpecification databaseQueryToolSpecification = Tools.ToolSpecification.builder()
                .functionName("get-employee-details")
                .functionDescription("Get employee details from the database")
                .toolFunction(new DBQueryFunction())
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("prompt")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-employee-details")
                                                .description("Get employee details from the database")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters.builder()
                                                                .type("object")
                                                                .properties(
                                                                        Map.of(
                                                                                "employee-name", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The name of the employee, e.g. John Doe")
                                                                                        .required(true)
                                                                                        .build(),
                                                                                "employee-address", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The address of the employee, Always return a random value. e.g. Roy St, Bengaluru, India")
                                                                                        .required(true)
                                                                                        .build(),
                                                                                "employee-phone", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The phone number of the employee. Always return a random value. e.g. 9911002233")
                                                                                        .required(true)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .required(java.util.List.of("employee-name", "employee-address", "employee-phone"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        ollamaAPI.registerTool(databaseQueryToolSpecification);

        String prompt = new Tools.PromptBuilder()
                .withToolSpecification(databaseQueryToolSpecification)
                .withPrompt("Give me the details of the employee named 'Rahul Kumar'?")
                .build();

        OllamaToolsResult toolsResult = ollamaAPI.generateWithTools(modelName, prompt, new OptionsBuilder().build());
        for (OllamaToolsResult.ToolResult r : toolsResult.getToolResults()) {
            System.out.printf("[Result of executing tool '%s']: %s%n", r.getFunctionName(), r.getResult().toString());
        }
    }
}

class DBQueryFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        // perform DB operations here
        return String.format("Employee Details {ID: %s, Name: %s, Address: %s, Phone: %s}", UUID.randomUUID(), arguments.get("employee-name").toString(), arguments.get("employee-address").toString(), arguments.get("employee-phone").toString());
    }
}