package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.*;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

@SuppressWarnings("DuplicatedCode")
public class ChatStreamingWithThinkingExample {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("qwen3:0.6b");

        OllamaChatRequest chatRequest =
                builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                        .withThinking(true)
                        .build();

        // Define a thinking stream handler
        OllamaGenerateTokenHandler thinkingStreamHandler =
                (s) -> {
                    System.out.print(s.toUpperCase());
                };
        // Define a response stream handler
        OllamaGenerateTokenHandler responseStreamHandler =
                (s) -> {
                    System.out.print(s.toLowerCase());
                };
        // pass the stream handlers to the chat method
        OllamaChatResult chatResult =
                ollamaAPI.chat(
                        chatRequest,
                        new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
        chatRequest =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(
                                OllamaChatMessageRole.USER, "And what is the second largest city?")
                        .withThinking(true)
                        .build();

        // "continue" conversation with model
        chatResult =
                ollamaAPI.chat(
                        chatRequest,
                        new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
    }
}
