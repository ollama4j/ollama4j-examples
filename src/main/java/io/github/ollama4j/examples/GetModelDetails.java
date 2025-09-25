package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.ModelDetail;
import io.github.ollama4j.utils.Utilities;

public class GetModelDetails {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);

        ModelDetail modelDetails = ollamaAPI.getModelDetails(model);

        System.out.println(modelDetails);
    }
}
