package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;

public class GenerateStreaming {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);

        OllamaGenerateTokenHandler streamHandler = new MyStreamHandler();

        new MyStreamingGenerator(model, ollamaAPI, streamHandler).start();
    }
}

class MyStreamHandler implements OllamaGenerateTokenHandler {

    private boolean firstToken = true;

    @Override
    public void accept(String message) {
        if (firstToken) {
            System.out.println("\n--- Streaming response begins ---\n");
            firstToken = false;
        }
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
                    OllamaGenerateRequestBuilder.builder().withModel(model).withPrompt("Give me the summary of learnings from Bhagawad Gita point wise.")
                            .withRaw(false).withThink(false).build(),
                    new OllamaGenerateStreamObserver(null, streamHandler));
        } catch (OllamaBaseException e) {
            throw new RuntimeException(e);
        }
    }
}
