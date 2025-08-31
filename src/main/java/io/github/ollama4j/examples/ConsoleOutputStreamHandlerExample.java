package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.impl.ConsoleOutputStreamHandler;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.generate.OllamaStreamHandler;

public class ConsoleOutputStreamHandlerExample {
    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";
        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(modelName);
        OllamaChatRequest requestModel = builder.withMessage(OllamaChatMessageRole.USER, "List all cricket world cup teams of 2019. Name the teams!")
                .build();

        // Define a stream handlers.
        OllamaStreamHandler thinkingStreamHandler = new ConsoleOutputStreamHandler();
        OllamaStreamHandler responseStreamHandler = new ConsoleOutputStreamHandler();

        ollamaAPI.chat(requestModel, thinkingStreamHandler, responseStreamHandler);
    }
}