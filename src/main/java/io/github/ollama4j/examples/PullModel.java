package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.net.URISyntaxException;

public class PullModel {

    public static void main(String[] args) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String model = Utilities.getFromConfig("TOOLS_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.pullModel(model);
    }
}