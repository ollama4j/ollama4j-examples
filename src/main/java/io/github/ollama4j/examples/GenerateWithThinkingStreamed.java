package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;

public class GenerateWithThinkingStreamed {

    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String modelName = Utilities.getFromConfig("THINKING_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(120);

        OllamaStreamHandler streamHandler = new ThinkingStreamHandler();

        new ThinkingModelStreamingGenerator(modelName, ollamaAPI, streamHandler).start();
    }
}

class ThinkingStreamHandler implements OllamaStreamHandler {

    private final StringBuffer response = new StringBuffer();

    @Override
    public void accept(String message) {
        String substr = message.substring(response.length());
        response.append(substr);
        System.out.print(substr);
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
                    "What is the capital of France", false, false, new OptionsBuilder().build(), streamHandler);
        } catch (OllamaBaseException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}