package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.*;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.Utilities;

public class ChatStreamingWithTokenConcatenationExample {

    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.setVerbose(false);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(OllamaModelType.LLAMA3);

        OllamaChatRequest chatRequest = builder.withMessage(OllamaChatMessageRole.USER, "Give me a summary of the book 'The Great Gatsby'")
                .build();

        ollamaAPI.chat(chatRequest, System.out::print);
    }
}
