package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.toolspecs.DatabaseQueryToolSpec;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class SimpleToolCallingExample {
    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";
        String model = "mistral:7b";
        askModel(host, model);
    }

    public static void askModel(String ollamaHost, String modelName) throws ToolInvocationException, OllamaBaseException, IOException, InterruptedException, URISyntaxException {
        OllamaAPI ollamaAPI = new OllamaAPI(ollamaHost);
        ollamaAPI.setRequestTimeoutSeconds(60);
        ollamaAPI.pullModel(modelName);

        Tools.ToolSpecification databaseQueryToolSpecification = DatabaseQueryToolSpec.getSpecification();

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