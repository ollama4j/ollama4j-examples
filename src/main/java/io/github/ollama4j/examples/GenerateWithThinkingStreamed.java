package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithThinkingStreamed {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "qwen3:0.6b";
        ollama.pullModel(model);

        OllamaGenerateTokenHandler thinkingStreamHandler = new ThinkingStreamHandler();
        OllamaGenerateTokenHandler responseStreamHandler = new ResponseStreamHandler();

        new ThinkingModelStreamingGenerator(
                        model, ollama, thinkingStreamHandler, responseStreamHandler)
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
    private final Ollama ollama;
    private final OllamaGenerateTokenHandler thinkingStreamHandler;
    private final OllamaGenerateTokenHandler responseStreamHandler;
    private final String model;

    ThinkingModelStreamingGenerator(
            String model,
            Ollama ollama,
            OllamaGenerateTokenHandler thinkingStreamHandler,
            OllamaGenerateTokenHandler responseStreamHandler) {
        this.ollama = ollama;
        this.model = model;
        this.thinkingStreamHandler = thinkingStreamHandler;
        this.responseStreamHandler = responseStreamHandler;
    }

    @Override
    public void run() {
        try {
            ollama.generate(
                    OllamaGenerateRequest.builder()
                            .withModel(model)
                            .withPrompt("What is the capital of France")
                            .withRaw(false)
                            .withThink(ThinkMode.ENABLED)
                            .build(),
                    new OllamaGenerateStreamObserver(
                            this.thinkingStreamHandler, this.responseStreamHandler));
        } catch (OllamaException e) {
            throw new RuntimeException(e);
        }
    }
}
