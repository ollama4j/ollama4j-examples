package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.embed.OllamaEmbedRequest;
import io.github.ollama4j.models.embed.OllamaEmbedResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;

public class GenerateEmbeddingsWithRequestModel {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "all-minilm";
        ollama.pullModel(model);

        OllamaEmbedRequest requestModel =
                new OllamaEmbedRequest(
                        model, Arrays.asList("Why is the sky blue?", "Why is the grass green?"));
        requestModel.setOptions(
                new OptionsBuilder().setSeed(42).setTemperature(0.7f).build().getOptionsMap());

        OllamaEmbedResult embeddings = ollama.embed(requestModel);

        System.out.println(embeddings.getEmbeddings());
    }
}
