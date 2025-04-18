package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.*;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.Utilities;

public class ChatStreamingWithTokenConcatenationExample {

    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.setVerbose(false);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("llama3.2:1b");

        OllamaChatRequest chatRequest = builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                .build();

        // Define a stream handler.
        // Note: This handler will receive each token and concatenates them with the previously received tokens and prints the concatenated string
        OllamaStreamHandler streamHandler = (s) -> {
            System.out.println(s);
        };

        // pass the stream handler to the chat method
        ollamaAPI.chat(chatRequest, streamHandler);
    }
}
