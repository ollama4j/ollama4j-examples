package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;

import java.io.IOException;

public class Generate {

    public static void main(String[] args) throws OllamaBaseException, IOException, InterruptedException {
        String host = "http://192.168.29.223:11434/";
        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = new OllamaAPI();

        OllamaResult result =
                ollamaAPI.generate(modelName, "Who are you?", null);

        System.out.println(result.getResponse());
    }
}