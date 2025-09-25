package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.request.CustomModelRequest;
import io.github.ollama4j.utils.Utilities;

public class CreateModel {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);

        ollamaAPI.createModel(
                CustomModelRequest.builder()
                        .model("mario")
                        .from(model)
                        .system("You are Mario from Super Mario Bros.")
                        .build());
    }
}
