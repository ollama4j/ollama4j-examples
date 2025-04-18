package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.ModelDetail;

import java.io.IOException;
import java.net.URISyntaxException;

public class GetModelDetails {

    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {

        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ModelDetail modelDetails = ollamaAPI.getModelDetails("qwen:0.5b");

        System.out.println(modelDetails);
    }
}