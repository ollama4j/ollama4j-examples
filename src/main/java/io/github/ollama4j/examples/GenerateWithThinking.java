package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithThinking {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "qwen3:0.6b";
        ollama.pullModel(model);

        boolean raw = false;
        boolean thinking = true;

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequest.builder()
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
