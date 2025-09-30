package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

public class Generate {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withPrompt("Who are you?")
                                .build(),
                        null);

        System.out.println(result.getResponse());
    }
}
