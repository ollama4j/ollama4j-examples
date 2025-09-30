package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.response.ModelDetail;
import io.github.ollama4j.utils.Utilities;

public class GetModelDetails {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        ModelDetail modelDetails = ollama.getModelDetails(model);

        System.out.println(modelDetails);
    }
}
