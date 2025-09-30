package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.*;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

@SuppressWarnings("DuplicatedCode")
public class ChatStreamingWithThinkingExample {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "qwen3:0.6b";
        ollama.pullModel(model);
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
                ollama.chat(
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
                ollama.chat(
                        chatRequest2,
                        new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
        boolean done = chatResult2.getResponseModel().isDone();
    }
}
