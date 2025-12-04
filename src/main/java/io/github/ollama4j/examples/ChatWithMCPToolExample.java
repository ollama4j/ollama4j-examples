package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessage;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResponseModel;
import io.github.ollama4j.models.chat.OllamaChatTokenHandler;
import io.github.ollama4j.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ChatWithMCPToolExample {
    public static void main(String[] args) throws Exception {
        Ollama ollama = new Ollama();
        ollama.setRequestTimeoutSeconds(120);

        // Load MCP tools from JSON file
        ollama.loadMCPToolsFromJson(Utilities.getResourcesFolderPath() + "mcp-config.json");

        // Create a user message
        OllamaChatMessage userMessage =
                new OllamaChatMessage(
                        OllamaChatMessageRole.USER, "Give me geocode of Bengaluru.");

        // Create a chat request
        OllamaChatRequest chatRequest =
                OllamaChatRequest.builder()
                        .withModel("mistral:7b")
                        .withUseTools(true)
                        .withMessages(new ArrayList<>(List.of(userMessage)))
                        .build();

        // Chat with the model
        ollama.chat(
                chatRequest,
                new OllamaChatTokenHandler() {
                    @Override
                    public void accept(OllamaChatResponseModel t) {
                        System.out.print(t.getMessage().getResponse());
                    }
                });
    }
}
