package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithThinking {

    public static void main(String[] args) throws Exception {

        String modelName = "qwen3:0.6b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        boolean raw = false;
        boolean thinking = true;

        OllamaResult result =
                ollamaAPI.generate(
                        modelName,
                        "Who are you?",
                        raw,
                        thinking,
                        new OptionsBuilder().build(),
                        new OllamaGenerateStreamObserver(null, null));

        System.out.println(result.getThinking());

        System.out.println(result.getResponse());
    }
}
