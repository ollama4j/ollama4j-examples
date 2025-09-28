package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;

public class PullModel {

    public static void main(String[] args) throws Exception {

        String model = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");

        ollamaAPI.pullModel(model);
    }
}
