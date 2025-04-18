package io.github.ollama4j.examples.toolcalling.annotated;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.tools.annotations.OllamaToolService;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;

@OllamaToolService(providers = GlobalConstantGenerator.class)
public class AnnotatedToolCallingExample {

    public static void main(String[] args) throws ToolInvocationException, OllamaBaseException, IOException, InterruptedException {
        String host = Utilities.getFromConfig("host");
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(60);
        ollamaAPI.setVerbose(false);

        String modelName = Utilities.getFromConfig("tools_model_mistral");

        // Inject the annotated method to the ollama tools-registry
        ollamaAPI.registerAnnotatedTools();

        // Alternatively, register a specific tool instance with annotated methods
        // ollamaAPI.registerAnnotatedTools(new GlobalConstantGenerator());

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(modelName);
        OllamaChatRequest requestModel = builder
                .withMessage(OllamaChatMessageRole.USER,
                        "Compute the most important constant in the world using 10 digits.")
                .build();

        OllamaChatResult chatResult = ollamaAPI.chat(requestModel);
        System.out.println("First answer: " + chatResult.getResponseModel().getMessage().getContent());

        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(OllamaChatMessageRole.USER, "Compute another most important constant in the world using 3 digits.").build();

        chatResult = ollamaAPI.chat(requestModel);
        System.out.println("Second answer: " + chatResult.getResponseModel().getMessage().getContent());
    }

}