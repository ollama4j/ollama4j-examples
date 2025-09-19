package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.io.IOException;

public class GenerateStreaming {
    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaGenerateTokenHandler streamHandler = new MyStreamHandler();

        new MyStreamingGenerator(modelName, ollamaAPI, streamHandler).start();
    }
}

class MyStreamHandler implements OllamaGenerateTokenHandler {

    @Override
    public void accept(String message) {
        System.out.print(message);
    }
}

class MyStreamingGenerator extends Thread {
    private final OllamaAPI ollamaAPI;
    private OllamaGenerateTokenHandler streamHandler = new MyStreamHandler();
    private String model;

    MyStreamingGenerator(
            String model, OllamaAPI ollamaAPI, OllamaGenerateTokenHandler streamHandler) {
        this.ollamaAPI = ollamaAPI;
        this.model = model;
        this.streamHandler = streamHandler;
    }

    @Override
    public void run() {
        try {
            ollamaAPI.generate(
                    model,
                    "What is the capital of France",
                    false,
                    false,
                    new OptionsBuilder().build(),
                    new OllamaGenerateStreamObserver(null, streamHandler));
        } catch (OllamaBaseException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
