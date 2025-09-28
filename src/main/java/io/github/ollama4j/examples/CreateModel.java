package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.request.CustomModelRequest;
import io.github.ollama4j.utils.Utilities;

public class CreateModel {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        ollama.createModel(
                CustomModelRequest.builder()
                        .model("mario")
                        .from(model)
                        .system("You are Mario from Super Mario Bros.")
                        .build());
    }
}
