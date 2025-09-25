package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.Model;
import io.github.ollama4j.utils.Utilities;
import java.util.List;

public class ListLocalModels {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();

        List<Model> models = ollamaAPI.listModels();

        models.forEach(model -> System.out.println(model.getName()));
    }
}
