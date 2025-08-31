package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;

public class GenerateWithThinkingStreamed {

    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = "http://192.168.29.223:11434/";
        String modelName = "gpt-oss:20b";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(120);

        OllamaStreamHandler streamHandler = new ThinkingStreamHandler();

        new ThinkingModelStreamingGenerator(modelName, ollamaAPI, streamHandler).start();
    }
}

class ThinkingStreamHandler implements OllamaStreamHandler {
    @Override
    public void accept(String message) {
        System.out.print(message);
    }
}

class ThinkingModelStreamingGenerator extends Thread {
    private final OllamaAPI ollamaAPI;
    private OllamaStreamHandler streamHandler = new MyStreamHandler();
    private String model;

    ThinkingModelStreamingGenerator(String model, OllamaAPI ollamaAPI, OllamaStreamHandler streamHandler) {
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