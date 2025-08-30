package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;

public class Generate {

    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String modelName = Utilities.getFromConfig("TOOLS_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        OllamaResult result =
                ollamaAPI.generate(modelName, "Who are you?", null);

        System.out.println(result.getResponse());
    }
}