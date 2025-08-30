package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;
import io.github.ollama4j.models.embeddings.OllamaEmbedResponseModel;

import java.util.Arrays;

public class EmbeddingsGenerationExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String modelName = Utilities.getFromConfig("TOOLS_MODEL");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.pullModel(modelName);

        OllamaEmbedResponseModel embeddings = ollamaAPI.embed(modelName, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));

        System.out.println(embeddings);
    }
}
