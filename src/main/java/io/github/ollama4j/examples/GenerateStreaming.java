package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

public class GenerateStreaming {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        OllamaGenerateTokenHandler streamHandler = new MyStreamHandler();

        new MyStreamingGenerator(model, ollama, streamHandler).start();
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
    private final Ollama ollama;
    private OllamaGenerateTokenHandler streamHandler = new MyStreamHandler();
    private String model;

    MyStreamingGenerator(String model, Ollama ollama, OllamaGenerateTokenHandler streamHandler) {
        this.ollama = ollama;
        this.model = model;
        this.streamHandler = streamHandler;
    }

    @Override
    public void run() {
        try {
            ollama.generate(
                    OllamaGenerateRequestBuilder.builder()
                            .withModel(model)
                            .withPrompt(
                                    "Give me the summary of learnings from Bhagawad Gita point"
                                            + " wise.")
                            .withRaw(false)
                            .withThink(false)
                            .build(),
                    new OllamaGenerateStreamObserver(null, streamHandler));
        } catch (OllamaException e) {
            throw new RuntimeException(e);
        }
    }
}
