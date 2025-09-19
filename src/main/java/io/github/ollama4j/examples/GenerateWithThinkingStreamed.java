package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.io.IOException;

public class GenerateWithThinkingStreamed {

    public static void main(String[] args) throws Exception {

        String modelName = "qwen3:0.6b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaGenerateTokenHandler thinkingStreamHandler = new ThinkingStreamHandler();
        OllamaGenerateTokenHandler responseStreamHandler = new ThinkingStreamHandler();

        new ThinkingModelStreamingGenerator(
                        modelName, ollamaAPI, thinkingStreamHandler, responseStreamHandler)
                .start();
    }
}

class ThinkingStreamHandler implements OllamaGenerateTokenHandler {
    @Override
    public void accept(String message) {
        System.out.print(message);
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
                    model,
                    "What is the capital of France",
                    false,
                    true,
                    new OptionsBuilder().build(),
                    new OllamaGenerateStreamObserver(
                            this.thinkingStreamHandler, this.responseStreamHandler));
        } catch (OllamaBaseException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
