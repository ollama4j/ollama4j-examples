package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

public class Generate {

    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaResult result =
                ollamaAPI.generate(
                        modelName,
                        "Who are you?",
                        false,
                        false,
                        new OptionsBuilder().build(),
                        new OllamaGenerateStreamObserver(null, null));

        System.out.println(result.getResponse());
    }
}
