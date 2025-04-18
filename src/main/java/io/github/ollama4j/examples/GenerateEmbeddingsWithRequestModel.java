package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.embeddings.OllamaEmbedRequestModel;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GenerateEmbeddingsWithRequestModel {

    public static void main(String[] args) throws IOException, OllamaBaseException, InterruptedException {

        String host = Utilities.getFromConfig("host");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        OllamaEmbedRequestModel requestModel = new OllamaEmbedRequestModel("all-minilm", Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        requestModel.setOptions(new OptionsBuilder().setSeed(42).setTemperature(0.7f).build().getOptionsMap());

        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(requestModel);

        System.out.println(embeddings.getEmbeddings());
    }
}