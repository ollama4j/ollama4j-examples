package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;

import java.util.Arrays;

public class EmbeddingsGenerationExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");

        String embeddingModelMinilm = Utilities.getFromConfig("embedding_model_minilm");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.pullModel(embeddingModelMinilm);

        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(embeddingModelMinilm, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));

        System.out.println(embeddings);
    }
}
