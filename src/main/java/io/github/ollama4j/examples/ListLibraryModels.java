package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.LibraryModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ListLibraryModels {
    public static void main(String[] args) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {

        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        List<LibraryModel> libraryModels = ollamaAPI.listModelsFromLibrary();

        libraryModels.forEach(System.out::println);
    }
}
