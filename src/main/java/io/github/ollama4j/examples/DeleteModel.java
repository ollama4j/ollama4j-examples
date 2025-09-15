package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeleteModel {
    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {
        String host = "http://192.168.29.223:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        

        ollamaAPI.deleteModel("mario", true);
    }
}
