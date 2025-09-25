package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.*;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

@SuppressWarnings("DuplicatedCode")
public class ChatStreamingWithThinkingExample {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);

        OllamaChatRequest chatRequest1 =
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
        OllamaChatResult chatResult1 =
                ollamaAPI.chat(
                        chatRequest1,
                        new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
        OllamaChatRequest chatRequest2 =
                builder.withMessages(chatResult1.getChatHistory())
                        .withMessage(
                                OllamaChatMessageRole.USER, "And what is the second largest city?")
                        .withThinking(true)
                        .build();

        // "continue" conversation with model
        OllamaChatResult chatResult2 =
                ollamaAPI.chat(
                        chatRequest2,
                        new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
        boolean done = chatResult2.getResponseModel().isDone();

    }
}
