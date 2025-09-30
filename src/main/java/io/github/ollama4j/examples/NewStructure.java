package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.chat.*;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;
import java.io.IOException;

public class NewStructure {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        generateStreaming(ollama);
        generateStreamingThinking(ollama);
        generateAsync(ollama);
        generate(ollama);
        chat(ollama);
        chatStreaming(ollama);
    }

    public static void generateAsync(Ollama ollama) throws Exception {
        OllamaAsyncResultStreamer resultStreamer =
                ollama.generateAsync("qwen3:0.6b", "Who are you", false, true);
        int pollIntervalMilliseconds = 1000;
        while (true) {
            String thinkingTokens = resultStreamer.getThinkingResponseStream().poll();
            String responseTokens = resultStreamer.getResponseStream().poll();
            System.out.print(thinkingTokens.toUpperCase());
            System.out.print(responseTokens.toLowerCase());
            Thread.sleep(pollIntervalMilliseconds);
            if (!resultStreamer.isAlive()) break;
        }
        System.out.println(
                "Complete thinking response: " + resultStreamer.getCompleteThinkingResponse());
        System.out.println("Complete response: " + resultStreamer.getCompleteResponse());
    }

    public static void chat(Ollama ollama) throws Exception {
        OllamaChatRequestBuilder builder =
                OllamaChatRequestBuilder.builder().withModel("qwen3:0.6b");

        // create first user question
        OllamaChatRequest requestModel =
                builder.withMessage(OllamaChatMessageRole.USER, "Tell me a small story")
                        .withThinking(true)
                        .build();
        // start conversation with model
        OllamaChatResult chatResult = ollama.chat(requestModel, null);
        System.out.println(
                "First thinking: "
                        + chatResult.getResponseModel().getMessage().getThinking().toUpperCase());
        System.out.println(
                "First answer: "
                        + chatResult.getResponseModel().getMessage().getResponse().toLowerCase());
        // create next userQuestion
        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withThinking(true)
                        .withMessage(OllamaChatMessageRole.USER, "And tell me more")
                        .build();
        // "continue" conversation with model
        chatResult = ollama.chat(requestModel, null);
        System.out.println(
                "Second thinking: "
                        + chatResult.getResponseModel().getMessage().getThinking().toUpperCase());
        System.out.println(
                "Second answer: "
                        + chatResult.getResponseModel().getMessage().getResponse().toLowerCase());
        System.out.println("Chat History: " + chatResult.getChatHistory());
    }

    public static void chatStreaming(Ollama ollama) throws Exception {
        OllamaChatRequestBuilder builder =
                OllamaChatRequestBuilder.builder().withModel("qwen3:0.6b");

        OllamaChatRequest chatRequest =
                builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                        .build();

        // Define a stream handler.
        // Note: This handler will receive each token and concatenates them with the
        // previously
        // received tokens and prints the concatenated string
        OllamaGenerateTokenHandler thinkingStreamHandler =
                (s) -> {
                    System.out.print(s.toUpperCase());
                };
        OllamaGenerateTokenHandler responseStreamHandler =
                (s) -> {
                    System.out.print(s.toLowerCase());
                };

        // pass the stream handler to the chat method
        OllamaChatResult res =
                ollama.chat(
                        chatRequest,
                        new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
        System.out.println(
                "\n\n[Full response]: " + res.getResponseModel().getMessage().getResponse());
    }

    public static void generate(Ollama ollama)
            throws OllamaException, IOException, InterruptedException {
        String model = "gemma3:270m";
        // String model = "qwen3:0.6b";
        OllamaResult ollamaResult =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withPrompt("Who are you")
                                .withRaw(false)
                                .withThink(false)
                                .build(),
                        null);
        // System.out.println("Thinking: " + ollamaResult.getThinking());
        // System.out.println("Response: " + ollamaResult.getResponse());
        System.out.println(ollamaResult);
    }

    public static void generateStreaming(Ollama ollama)
            throws OllamaException, IOException, InterruptedException {
        OllamaResult ollamaResult =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel("qwen3:0.6b")
                                .withPrompt("Who are you")
                                .withRaw(false)
                                .withThink(false)
                                .build(),
                        new OllamaGenerateStreamObserver(null, null));
        System.out.println("Thinking: " + ollamaResult.getThinking());
        System.out.println("Response: " + ollamaResult.getResponse());
    }

    public static void generateStreamingThinking(Ollama ollama)
            throws OllamaException, IOException, InterruptedException {
        OllamaResult ollamaResult =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel("qwen3:0.6b")
                                .withPrompt("Who are you")
                                .withRaw(false)
                                .withThink(true)
                                .build(),
                        new OllamaGenerateStreamObserver(
                                null,
                                (responseToken) -> System.out.print(responseToken.toLowerCase())));
        System.out.println("Thinking: " + ollamaResult.getThinking());
        System.out.println("Response: " + ollamaResult.getResponse());
    }
}
