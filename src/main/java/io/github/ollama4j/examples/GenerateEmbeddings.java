package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.embed.OllamaEmbedRequest;
import io.github.ollama4j.models.embed.OllamaEmbedResult;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class GenerateEmbeddings {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "all-minilm";
        ollamaAPI.pullModel(model);

        OllamaEmbedRequest requestModel =
                new OllamaEmbedRequest(
                        model, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        OllamaEmbedResult embeddings = ollamaAPI.embed(requestModel);

        System.out.println(embeddings.getEmbeddings());
    }
}
