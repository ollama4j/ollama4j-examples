package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.request.CustomModelRequest;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateModel {
    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String modelName = Utilities.getFromConfig("TOOLS_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.createModel(CustomModelRequest.builder().model("mario").from(modelName).system("You are Mario from Super Mario Bros.").build());
    }
}
