package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.util.List;

public class GenerateWithImageURL {

    public static void main(String[] args) throws Exception {

        String modelName = "moondream:1.8b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        ollamaAPI.setImageURLConnectTimeoutSeconds(30);
        ollamaAPI.setImageURLReadTimeoutSeconds(30);

        nonStreamingWithURL(ollamaAPI, modelName);
    }

    public static void nonStreamingWithURL(OllamaAPI ollamaAPI, String modelName) throws Exception {
        OllamaResult result =
                ollamaAPI.generateWithImages(
                        modelName,
                        "What's in this image?",
                        List.of(
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/York%C5%A1%C3%ADrsk%C3%BD_teri%C3%A9r_%282%29.jpg/1280px-York%C5%A1%C3%ADrsk%C3%BD_teri%C3%A9r_%282%29.jpg"),
                        new OptionsBuilder().build(),
                        null,
                        null);
        System.out.println(result.getResponse());
    }

    public static void streamingWithFile(OllamaAPI ollamaAPI, String modelName) throws Exception {}
}
