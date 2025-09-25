package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.tools.toolspecs.EmployeeFinderToolSpec;
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

        Tools.Tool toolSpecification = EmployeeFinderToolSpec.getSpecification();

        ollamaAPI.registerTool(toolSpecification);

        String prompt = "Give me the details of the employee named 'Rahul Kumar'?";
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
}
