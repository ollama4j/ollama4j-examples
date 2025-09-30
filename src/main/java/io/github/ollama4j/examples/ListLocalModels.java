package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.response.Model;
import io.github.ollama4j.utils.Utilities;
import java.util.List;

public class ListLocalModels {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");

        List<Model> models = ollama.listModels();

        models.forEach(model -> System.out.println(model.getName()));
    }
}
