package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class PullModel {

    public static void main(String[] args) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.pullModel("gemma3");
    }
}