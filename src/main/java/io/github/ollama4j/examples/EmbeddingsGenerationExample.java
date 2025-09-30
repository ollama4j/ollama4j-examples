package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.embed.OllamaEmbedRequest;
import io.github.ollama4j.models.embed.OllamaEmbedResult;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class EmbeddingsGenerationExample {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);
        OllamaEmbedRequest requestModel =
                new OllamaEmbedRequest(
                        model, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        OllamaEmbedResult embeddings = ollama.embed(requestModel);

        System.out.println(embeddings);
    }
}
