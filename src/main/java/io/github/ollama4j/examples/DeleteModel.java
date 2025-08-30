package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeleteModel {
    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {
        String host = Utilities.getFromConfig("OLLAMA_HOST");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.setVerbose(false);

        ollamaAPI.deleteModel("mario", true);
    }
}
