package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;

public class GenerateAsync {

    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";
        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = new OllamaAPI();
        
        ollamaAPI.setRequestTimeoutSeconds(60);

        String prompt = "List all cricket world cup teams of 2019.";

        boolean raw = false;
        boolean think = false;

        OllamaAsyncResultStreamer resultStreamer = ollamaAPI.generate(modelName, prompt, raw, think);

        int pollIntervalMilliseconds = 1000;
        while (true) {
            String responseTokens = resultStreamer.getResponseStream().poll();
            System.out.print(responseTokens != null ? responseTokens : "");
            Thread.sleep(pollIntervalMilliseconds);
            if (!resultStreamer.isAlive())
                break;
        }
        System.out.println("Complete response: " + resultStreamer.getCompleteResponse());
    }
}