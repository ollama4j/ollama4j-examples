package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

public class ChatStreamingExample {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "gemma3:270m";
        ollamaAPI.pullModel(model);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);

        OllamaChatRequest chatRequest =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "Give me a summary of the book 'The Great Gatsby'")
                        .build();

        // Define a stream observer.
        OllamaChatStreamObserver streamObserver = new OllamaChatStreamObserver();

        // If thinking tokens are found, print them in lowercase :)
        streamObserver.setThinkingStreamHandler(
                new OllamaGenerateTokenHandler() {
                    @Override
                    public void accept(String message) {
                        System.out.print(message.toUpperCase());
                    }
                });
        // Response tokens to be printed in lowercase
        streamObserver.setResponseStreamHandler(
                new OllamaGenerateTokenHandler() {
                    @Override
                    public void accept(String message) {
                        System.out.print(message.toLowerCase());
                    }
                });

        ollamaAPI.chat(chatRequest, streamObserver);
    }
}
