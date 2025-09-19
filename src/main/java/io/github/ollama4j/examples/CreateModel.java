package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.request.CustomModelRequest;
import io.github.ollama4j.utils.Utilities;

public class CreateModel {
    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        ollamaAPI.createModel(
                CustomModelRequest.builder()
                        .model("mario")
                        .from(modelName)
                        .system("You are Mario from Super Mario Bros.")
                        .build());
    }
}
