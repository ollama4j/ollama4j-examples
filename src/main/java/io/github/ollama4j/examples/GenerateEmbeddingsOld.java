package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;
import java.util.List;

public class GenerateEmbeddingsOld {

    public static void main(String[] args) throws IOException, OllamaBaseException, InterruptedException {

        String host = Utilities.getFromConfig("host");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        List<Double> embeddings = ollamaAPI.generateEmbeddings("all-minilm",
                "Here is an article about llamas...");

        System.out.println(embeddings);
    }
}