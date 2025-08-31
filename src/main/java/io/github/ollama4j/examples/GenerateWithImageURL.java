package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class GenerateWithImageURL {

    public static void main(String[] args) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        String host = "http://192.168.29.223:11434/";
        String modelName = "moondream:1.8b";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);
        ollamaAPI.setRequestTimeoutSeconds(10);

        OllamaResult result = ollamaAPI.generateWithImageURLs(modelName,
                "What's in this image?",
                List.of(
                        "https://t3.ftcdn.net/jpg/02/96/63/80/360_F_296638053_0gUVA4WVBKceGsIr7LNqRWSnkusi07dq.jpg"),
                new OptionsBuilder().build()
        );
        System.out.println(result.getResponse());
    }
}