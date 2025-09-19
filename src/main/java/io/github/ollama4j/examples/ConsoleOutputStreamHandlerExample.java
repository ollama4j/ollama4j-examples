package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.impl.ConsoleOutputGenerateTokenHandler;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

public class ConsoleOutputStreamHandlerExample {
    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(modelName);
        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "List all cricket world cup teams of 2019. Name the teams!")
                        .build();

        // Define a stream handlers.
        OllamaGenerateTokenHandler thinkingStreamHandler = new ConsoleOutputGenerateTokenHandler();
        OllamaGenerateTokenHandler responseStreamHandler = new ConsoleOutputGenerateTokenHandler();

        ollamaAPI.chat(
                requestModel,
                new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
    }
}
