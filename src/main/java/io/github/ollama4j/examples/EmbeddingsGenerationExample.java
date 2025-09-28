package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.embed.OllamaEmbedRequest;
import io.github.ollama4j.models.embed.OllamaEmbedResponse;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class EmbeddingsGenerationExample {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);
        OllamaEmbedRequest requestModel =
                new OllamaEmbedRequest(
                        model, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        OllamaEmbedResponse embeddings = ollamaAPI.embed(requestModel);

        System.out.println(embeddings);
    }
}
