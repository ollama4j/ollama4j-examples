package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.utils.Utilities;

public class ChatStreamingExample {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gemma3:270m");

        OllamaChatRequest chatRequest =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "Give me a summary of the book 'The Great Gatsby'")
                        .build();

        // Define a stream handler.
        // Note: This API will allow a handler to receive each token separately without
        // concatenating with previously received tokens.
        ollamaAPI.chat(chatRequest, token -> System.out.print(token.getMessage().getResponse()));
    }
}
