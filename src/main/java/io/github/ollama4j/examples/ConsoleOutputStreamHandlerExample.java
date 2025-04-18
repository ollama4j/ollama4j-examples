package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.impl.ConsoleOutputStreamHandler;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.generate.OllamaStreamHandler;

public class ConsoleOutputStreamHandlerExample {
    public static void main(String[] args) throws Exception {
        String host = "http://localhost:11434/";
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("llama3.2:1b");
        OllamaChatRequest requestModel = builder.withMessage(OllamaChatMessageRole.USER, "List all cricket world cup teams of 2019. Name the teams!")
                .build();

        // Define a stream handler.
        // Note: This handler will receive each token and prints it to the console without string concatenation with previously received tokens.
        OllamaStreamHandler streamHandler = new ConsoleOutputStreamHandler();
        ollamaAPI.chat(requestModel, streamHandler);
    }
}