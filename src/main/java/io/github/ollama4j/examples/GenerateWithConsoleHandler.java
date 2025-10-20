package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.impl.ConsoleOutputGenerateTokenHandler;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

public class GenerateWithConsoleHandler {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequest.builder()
                                .withModel(model)
                                .withPrompt("Who are you?")
                                .build(),
                        new OllamaGenerateStreamObserver(
                                new ConsoleOutputGenerateTokenHandler(),
                                new ConsoleOutputGenerateTokenHandler()));

        System.out.println(result.getResponse());
    }
}
