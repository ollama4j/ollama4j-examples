package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;

public class GenerateStreamingWithTokenConcatenation {
    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = "http://192.168.29.223:11434/";
        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);

        OllamaStreamHandler streamHandler = new MyStreamHandler();

        new MyStreamingGenerator(modelName, ollamaAPI, streamHandler).start();
    }
}

class MyStreamHandler implements OllamaStreamHandler {

    @Override
    public void accept(String message) {
        System.out.print(message);
    }
}

class MyStreamingGenerator extends Thread {
    private final OllamaAPI ollamaAPI;
    private OllamaStreamHandler streamHandler = new MyStreamHandler();
    private String model;

    MyStreamingGenerator(String model, OllamaAPI ollamaAPI, OllamaStreamHandler streamHandler) {
        this.ollamaAPI = ollamaAPI;
        this.model = model;
        this.streamHandler = streamHandler;
    }

    @Override
    public void run() {
        try {
            ollamaAPI.generate(model,
                    "What is the capital of France", false, new OptionsBuilder().build(), streamHandler);
        } catch (OllamaBaseException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}