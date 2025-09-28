package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

public class Generate {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);

        OllamaResult result =
                ollamaAPI.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withPrompt("Who are you?")
                                .build(),
                        null);

        System.out.println(result.getResponse());
    }
}
