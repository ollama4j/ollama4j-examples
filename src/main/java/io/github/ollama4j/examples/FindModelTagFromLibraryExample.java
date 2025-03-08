package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.utils.Utilities;
import io.github.ollama4j.models.response.LibraryModelTag;

public class FindModelTagFromLibraryExample {

    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        LibraryModelTag libraryModelTag = ollamaAPI.findModelTagFromLibrary("qwen2.5", "7b");

        System.out.println(libraryModelTag);
    }
}

