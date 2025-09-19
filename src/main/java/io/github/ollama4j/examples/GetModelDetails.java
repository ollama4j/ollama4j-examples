package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.ModelDetail;
import io.github.ollama4j.utils.Utilities;

public class GetModelDetails {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();

        ModelDetail modelDetails = ollamaAPI.getModelDetails("mistral:7b");

        System.out.println(modelDetails);
    }
}
