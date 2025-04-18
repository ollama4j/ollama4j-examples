package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.LibraryModelTag;

import java.io.IOException;
import java.net.URISyntaxException;

public class FindLibraryModel {
    public static void main(String[] args) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {

        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        LibraryModelTag libraryModelTag = ollamaAPI.findModelTagFromLibrary("qwen2.5", "7b");

        System.out.println(libraryModelTag);
    }
}
