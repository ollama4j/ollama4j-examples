package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.embed.OllamaEmbedRequest;
import io.github.ollama4j.models.embed.OllamaEmbedResult;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class GenerateEmbeddings {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "all-minilm";
        ollamaAPI.pullModel(model);

        OllamaEmbedRequest requestModel =
                new OllamaEmbedRequest(
                        model, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        OllamaEmbedResult embeddings = ollamaAPI.embed(requestModel);

        System.out.println(embeddings.getEmbeddings());
    }
}
