package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.request.CustomModelRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateModel {
    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {
        String host = "http://192.168.29.223:11434/";
        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.createModel(CustomModelRequest.builder().model("mario").from(modelName).system("You are Mario from Super Mario Bros.").build());
    }
}
