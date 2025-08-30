package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.impl.ConsoleOutputStreamHandler;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.utils.Utilities;

public class ConsoleOutputStreamHandlerExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String modelName = Utilities.getFromConfig("TOOLS_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(modelName);
        OllamaChatRequest requestModel = builder.withMessage(OllamaChatMessageRole.USER, "List all cricket world cup teams of 2019. Name the teams!")
                .build();

        // Define a stream handler.
        // Note: This handler will receive each token and prints it to the console without string concatenation with previously received tokens.
        OllamaStreamHandler streamHandler = new ConsoleOutputStreamHandler();
        ollamaAPI.chat(requestModel, streamHandler);
    }
}