package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.embeddings.OllamaEmbedRequestModel;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class EmbeddingsGenerationExample {
    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        ollamaAPI.pullModel(modelName);
        OllamaEmbedRequestModel model =
                new OllamaEmbedRequestModel(
                        modelName,
                        Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(model);

        System.out.println(embeddings);
    }
}
