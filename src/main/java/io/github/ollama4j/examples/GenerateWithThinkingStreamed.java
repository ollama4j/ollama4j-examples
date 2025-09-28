package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithThinkingStreamed {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);

        OllamaGenerateTokenHandler thinkingStreamHandler = new ThinkingStreamHandler();
        OllamaGenerateTokenHandler responseStreamHandler = new ResponseStreamHandler();

        new ThinkingModelStreamingGenerator(
                        model, ollamaAPI, thinkingStreamHandler, responseStreamHandler)
                .start();
    }
}

class ThinkingStreamHandler implements OllamaGenerateTokenHandler {
    @Override
    public void accept(String message) {
        System.out.print(message.toUpperCase());
    }
}

class ResponseStreamHandler implements OllamaGenerateTokenHandler {
    @Override
    public void accept(String message) {
        System.out.print(message.toLowerCase());
    }
}

class ThinkingModelStreamingGenerator extends Thread {
    private final OllamaAPI ollamaAPI;
    private final OllamaGenerateTokenHandler thinkingStreamHandler;
    private final OllamaGenerateTokenHandler responseStreamHandler;
    private final String model;

    ThinkingModelStreamingGenerator(
            String model,
            OllamaAPI ollamaAPI,
            OllamaGenerateTokenHandler thinkingStreamHandler,
            OllamaGenerateTokenHandler responseStreamHandler) {
        this.ollamaAPI = ollamaAPI;
        this.model = model;
        this.thinkingStreamHandler = thinkingStreamHandler;
        this.responseStreamHandler = responseStreamHandler;
    }

    @Override
    public void run() {
        try {
            ollamaAPI.generate(
                    OllamaGenerateRequestBuilder.builder()
                            .withModel(model)
                            .withPrompt("What is the capital of France")
                            .withRaw(false)
                            .withThink(true)
                            .build(),
                    new OllamaGenerateStreamObserver(
                            this.thinkingStreamHandler, this.responseStreamHandler));
        } catch (OllamaException e) {
            throw new RuntimeException(e);
        }
    }
}
