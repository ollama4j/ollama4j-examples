package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.utils.Utilities;

public class GenerateAsyncWithThinking {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);

        String prompt = "How long does it take for the light from the Sun to reach Earth?";

        boolean raw = false;
        boolean think = true;

        OllamaAsyncResultStreamer resultStreamer =
                ollamaAPI.generateAsync(model, prompt, raw, think);

        int pollIntervalMilliseconds = 1000;
        while (true) {
            String thinkingTokens = resultStreamer.getThinkingResponseStream().poll();
            String responseTokens = resultStreamer.getResponseStream().poll();
            System.out.print(thinkingTokens != null ? thinkingTokens.toUpperCase() : "");
            System.out.print(responseTokens != null ? responseTokens.toLowerCase() : "");
            Thread.sleep(pollIntervalMilliseconds);
            if (!resultStreamer.isAlive()) break;
        }
        System.out.println(
                "Complete thinking response: " + resultStreamer.getCompleteThinkingResponse());
        System.out.println("Complete response: " + resultStreamer.getCompleteResponse());
    }
}
