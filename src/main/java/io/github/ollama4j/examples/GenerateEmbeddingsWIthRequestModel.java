package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.embeddings.OllamaEmbedRequestModel;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.util.Arrays;

public class GenerateEmbeddingsWIthRequestModel {

    public static void main(String[] args) throws IOException, OllamaBaseException, InterruptedException {

        String host = Utilities.getFromConfig("host");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(new OllamaEmbedRequestModel("all-minilm", Arrays.asList("Why is the sky blue?", "Why is the grass green?")));

        System.out.println(embeddings.getEmbeddings());
    }
}