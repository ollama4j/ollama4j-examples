package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.types.OllamaModelType;

public class GenerateAsync {

    public static void main(String[] args) throws Exception {
        String host = "http://localhost:11434/";
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(60);
        String prompt = "List all cricket world cup teams of 2019.";
        OllamaAsyncResultStreamer streamer = ollamaAPI.generateAsync("llama3.2:1b", prompt, false);

        // Set the poll interval according to your need.
        // Smaller the poll interval, more frequently you receive the tokens.
        int pollIntervalMilliseconds = 1000;

        while (true) {
            String tokens = streamer.getStream().poll();
            System.out.print(tokens);
            if (!streamer.isAlive()) {
                break;
            }
            Thread.sleep(pollIntervalMilliseconds);
        }

        // Get the full response as a string
        // System.out.println(streamer.getCompleteResponse());
    }
}