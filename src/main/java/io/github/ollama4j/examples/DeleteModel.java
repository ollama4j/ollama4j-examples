package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.utils.Utilities;

public class DeleteModel {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "mario";
        ollama.deleteModel(model, true);
    }
}
