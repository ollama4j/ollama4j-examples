package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.utils.Utilities;

public class GenerateAsync {

    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        String prompt = "List all cricket world cup teams of 2019.";

        boolean raw = false;
        boolean think = false;

        OllamaAsyncResultStreamer resultStreamer =
                ollamaAPI.generateAsync(modelName, prompt, raw, think);

        int pollIntervalMilliseconds = 1000;
        while (true) {
            String responseTokens = resultStreamer.getResponseStream().poll();
            System.out.print(responseTokens != null ? responseTokens : "");
            Thread.sleep(pollIntervalMilliseconds);
            if (!resultStreamer.isAlive()) break;
        }
        System.out.println("Complete response: " + resultStreamer.getCompleteResponse());
    }
}
