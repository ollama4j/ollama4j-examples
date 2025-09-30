package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.utils.Utilities;

public class GenerateAsync {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        String prompt = "List all cricket world cup teams of 2019.";

        boolean raw = false;
        boolean think = false;

        OllamaAsyncResultStreamer resultStreamer = ollama.generateAsync(model, prompt, raw, think);

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
