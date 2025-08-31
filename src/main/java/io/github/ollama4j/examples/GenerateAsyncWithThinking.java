package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;

public class GenerateAsyncWithThinking {

    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";
        String modelName = "gpt-oss:20b";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(true);
        ollamaAPI.setRequestTimeoutSeconds(60);

        String prompt = "How long does it take for the light from the Sun to reach Earth?";

        boolean raw = false;
        boolean think = true;

        OllamaAsyncResultStreamer resultStreamer = ollamaAPI.generateAsync(modelName, prompt, raw, think);

        int pollIntervalMilliseconds = 1000;
        while (true) {
            String thinkingTokens = resultStreamer.getThinkingResponseStream().poll();
            String responseTokens = resultStreamer.getResponseStream().poll();
            System.out.print(thinkingTokens != null ? thinkingTokens.toUpperCase() : "");
            System.out.print(responseTokens != null ? responseTokens.toLowerCase() : "");
            Thread.sleep(pollIntervalMilliseconds);
            if (!resultStreamer.isAlive())
                break;
        }
        System.out.println("Complete thinking response: " + resultStreamer.getCompleteThinkingResponse());
        System.out.println("Complete response: " + resultStreamer.getCompleteResponse());
    }
}