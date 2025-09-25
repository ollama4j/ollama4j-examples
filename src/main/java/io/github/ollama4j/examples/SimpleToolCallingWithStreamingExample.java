package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.ToolFunction;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimpleToolCallingWithStreamingExample {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        askModel(ollamaAPI, model);
    }

    public static void askModel(OllamaAPI ollamaAPI, String modelName)
            throws
            OllamaBaseException,
            IOException {

        ollamaAPI.pullModel(modelName);

        Tools.ToolSpecification toolSpecification = employeeFinderTool();

        ollamaAPI.registerTool(toolSpecification);

        String prompt =
                new Tools.PromptBuilder()
                        .withToolSpecification(toolSpecification)
                        .withPrompt("Give me the details of the employee named 'Rahul Kumar'?")
                        .build();
        OllamaGenerateTokenHandler handler =
                new OllamaGenerateTokenHandler() {
                    @Override
                    public void accept(String message) {
                        System.out.print(message.toUpperCase());
                    }
                };
        OllamaResult toolsResult =
                ollamaAPI.generate(OllamaGenerateRequestBuilder.builder().withModel(modelName).withPrompt(prompt).withOptions(new OptionsBuilder().build()).build(), new OllamaGenerateStreamObserver(null, handler));
        // for (OllamaToolsResult.ToolResult r : toolsResult.getToolResults()) {
        //     System.out.printf(
        //             "[Result of executing tool '%s']: %s%n",
        //             r.getFunctionName(), r.getResult().toString());
        // }
    }

    private static Tools.ToolSpecification employeeFinderTool() {
        return Tools.ToolSpecification.builder()
                .functionName("get-employee-details")
                .functionDescription("Get details for a person or an employee")
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("function")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-employee-details")
                                                .description(
                                                        "Get details for a person or an employee")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters
                                                                .builder()
                                                                .type("object")
                                                                .properties(
                                                                        new Tools.PropsBuilder()
                                                                                .withProperty(
                                                                                        "employee-name",
                                                                                        Tools
                                                                                                .PromptFuncDefinition
                                                                                                .Property
                                                                                                .builder()
                                                                                                .type(
                                                                                                        "string")
                                                                                                .description(
                                                                                                        "The name"
                                                                                                                + " of the"
                                                                                                                + " employee,"
                                                                                                                + " e.g."
                                                                                                                + " John"
                                                                                                                + " Doe")
                                                                                                .required(
                                                                                                        true)
                                                                                                .build())
                                                                                .withProperty(
                                                                                        "employee-address",
                                                                                        Tools
                                                                                                .PromptFuncDefinition
                                                                                                .Property
                                                                                                .builder()
                                                                                                .type(
                                                                                                        "string")
                                                                                                .description(
                                                                                                        "The address"
                                                                                                                + " of the"
                                                                                                                + " employee,"
                                                                                                                + " Always"
                                                                                                                + " eturns"
                                                                                                                + " a random"
                                                                                                                + " address."
                                                                                                                + " For example,"
                                                                                                                + " Church"
                                                                                                                + " St, Bengaluru,"
                                                                                                                + " India")
                                                                                                .required(
                                                                                                        true)
                                                                                                .build())
                                                                                .withProperty(
                                                                                        "employee-phone",
                                                                                        Tools
                                                                                                .PromptFuncDefinition
                                                                                                .Property
                                                                                                .builder()
                                                                                                .type(
                                                                                                        "string")
                                                                                                .description(
                                                                                                        "The phone"
                                                                                                                + " number"
                                                                                                                + " of the"
                                                                                                                + " employee."
                                                                                                                + " Always"
                                                                                                                + " returns"
                                                                                                                + " a random"
                                                                                                                + " phone"
                                                                                                                + " number."
                                                                                                                + " For example,"
                                                                                                                + " 9911002233")
                                                                                                .required(
                                                                                                        true)
                                                                                                .build())
                                                                                .build())
                                                                .required(List.of("employee-name"))
                                                                .build())
                                                .build())
                                .build())
                .toolFunction(
                        new ToolFunction() {
                            @Override
                            public Object apply(Map<String, Object> arguments) {
//                                System.out.println(
//                                        "Invoking employee finder tool with arguments: "
//                                                + arguments);
                                String employeeName = "Random Employee";
                                if (arguments.containsKey("employee-name")) {
                                    employeeName = arguments.get("employee-name").toString();
                                }
                                String address = null;
                                String phone = null;
                                if (employeeName.equalsIgnoreCase("Rahul Kumar")) {
                                    address = "Pune, Maharashtra, India";
                                    phone = "9911223344";
                                } else {
                                    address = "Karol Bagh, Delhi, India";
                                    phone = "9911002233";
                                }
                                // perform DB operations here
                                return String.format(
                                        "Employee Details {ID: %s, Name: %s, Address: %s, Phone:"
                                                + " %s}",
                                        UUID.randomUUID(), employeeName, address, phone);
                            }
                        })
                .build();
    }
}
