package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;

public class DeleteModel {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mario";
        ollamaAPI.deleteModel(model, true);
    }
}
