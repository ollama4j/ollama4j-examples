package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.embeddings.OllamaEmbedRequestModel;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class GenerateEmbeddingsWithRequestModel {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaEmbedRequestModel requestModel =
                new OllamaEmbedRequestModel(
                        "all-minilm",
                        Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        requestModel.setOptions(
                new OptionsBuilder().setSeed(42).setTemperature(0.7f).build().getOptionsMap());

        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(requestModel);

        System.out.println(embeddings.getEmbeddings());
    }
}
