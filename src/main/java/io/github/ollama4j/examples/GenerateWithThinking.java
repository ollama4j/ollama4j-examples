package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;

public class GenerateWithThinking {

    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String modelName = Utilities.getFromConfig("THINKING_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(120);

        boolean raw = false;
        boolean thinking = true;

        OllamaResult result =
                ollamaAPI.generate(modelName, "Who are you?", raw, thinking, new OptionsBuilder().build());

        System.out.println(result.getThinking());

        System.out.println(result.getResponse());
    }
}