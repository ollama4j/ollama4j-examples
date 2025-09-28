package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithThinking {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);

        boolean raw = false;
        boolean thinking = true;

        OllamaResult result =
                ollamaAPI.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withPrompt("Who are you?")
                                .withRaw(raw)
                                .withThink(thinking)
                                .build(),
                        null);

        System.out.println(result.getThinking().toUpperCase());

        System.out.println(result.getResponse().toLowerCase());
    }
}
