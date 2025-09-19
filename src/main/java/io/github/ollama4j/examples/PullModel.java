package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;

public class PullModel {

    public static void main(String[] args) throws Exception {

        String model = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        ollamaAPI.pullModel(model);
    }
}
