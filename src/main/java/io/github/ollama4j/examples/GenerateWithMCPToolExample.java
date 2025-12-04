package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithMCPToolExample {
    public static void main(String[] args) throws Exception {
        Ollama ollama = new Ollama();
        ollama.setRequestTimeoutSeconds(120);

        // Load MCP tools from JSON file
        ollama.loadMCPToolsFromJson(Utilities.getResourcesFolderPath() + "mcp-config.json");

        // Create a user message
        String userMessage = "Give me geocode of Bengaluru.";

        // Create a chat request
        OllamaGenerateRequest generateRequest =
                OllamaGenerateRequest.builder()
                        .withModel("mistral:7b")
                        .withUseTools(true)
                        .withPrompt(userMessage)
                        .build();

        // Chat with the model
        ollama.generate(
                generateRequest,
                new OllamaGenerateStreamObserver(
                        new OllamaGenerateTokenHandler() {
                            @Override
                            public void accept(String t) {
                                System.out.print(t);
                            }
                        },
                        new OllamaGenerateTokenHandler() {
                            @Override
                            public void accept(String t) {
                                System.out.print(t);
                            }
                        }));
    }
}
