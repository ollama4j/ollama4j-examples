package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.embeddings.OllamaEmbedRequestModel;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;

import java.io.IOException;
import java.util.Arrays;

public class GenerateEmbeddings {

    public static void main(String[] args) throws IOException, OllamaBaseException, InterruptedException {
        String host = "http://192.168.29.223:11434/";
        String modelName = "all-minilm";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        OllamaEmbedRequestModel model = new OllamaEmbedRequestModel(modelName, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(model);

        System.out.println(embeddings.getEmbeddings());
    }
}