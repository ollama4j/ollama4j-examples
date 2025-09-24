package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.toolspecs.DatabaseQueryToolSpec;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.net.URISyntaxException;

public class SimpleToolCallingExample {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        askModel(ollamaAPI, model);
    }

    public static void askModel(OllamaAPI ollamaAPI, String modelName)
            throws ToolInvocationException,
            OllamaBaseException,
            IOException,
            InterruptedException,
            URISyntaxException {

        ollamaAPI.pullModel(modelName);

        Tools.ToolSpecification databaseQueryToolSpecification =
                DatabaseQueryToolSpec.getSpecification();

        ollamaAPI.registerTool(databaseQueryToolSpecification);

        String prompt =
                new Tools.PromptBuilder()
                        .withToolSpecification(databaseQueryToolSpecification)
                        .withPrompt("Give me the details of the employee named 'Rahul Kumar'?")
                        .build();

        OllamaGenerateRequest request =
                OllamaGenerateRequestBuilder.builder()
                        .withModel(modelName)
                        .withPrompt(prompt)
                        .withOptions(new OptionsBuilder().build())
                        .build();
        OllamaGenerateStreamObserver handler = null;

        OllamaResult toolsResult = ollamaAPI.generate(request, handler);
        System.out.printf(
                "[Result of executing tool]: %s%n",
                toolsResult.getResponse());
    }
}
