package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.impl.ConsoleOutputGenerateTokenHandler;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateWithImageFile {

    public static void main(String[] args) throws Exception {
        String modelName = "moondream:1.8b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        nonStreamingWithFile(ollamaAPI, modelName);
        streamingWithFile(ollamaAPI, modelName);
        nonStreamingWithFileAndFormat(ollamaAPI, modelName);
    }

    public static void nonStreamingWithFile(OllamaAPI ollamaAPI, String modelName)
            throws Exception {
        // Load image from resources and copy to a temporary file
        InputStream is = GenerateWithImageFile.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        OllamaResult result = ollamaAPI.generate(OllamaGenerateRequestBuilder.builder().withModel(modelName)
                .withPrompt("What's in this image?").withImages(List.of(tempImageFile)).build(), null);
        System.out.println(result.getResponse());
    }

    public static void streamingWithFile(OllamaAPI ollamaAPI, String modelName) throws Exception {
        // Load image from resources and copy to a temporary file
        InputStream is = GenerateWithImageFile.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        OllamaResult result = ollamaAPI.generate(OllamaGenerateRequestBuilder.builder().withModel(modelName).withPrompt("What's in this image?").withImages(List.of(tempImageFile)).build(), new OllamaGenerateStreamObserver(null, new ConsoleOutputGenerateTokenHandler()));
        System.out.println(result.getResponse());
    }

    public static void nonStreamingWithFileAndFormat(OllamaAPI ollamaAPI, String modelName)
            throws Exception {
        // Load image from resources and copy to a temporary file
        InputStream is = GenerateWithImageFile.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Map<String, Object> format = new HashMap<>();
        format.put("type", "object");
        format.put(
                "properties",
                new HashMap<String, Object>() {
                    {
                        put(
                                "title",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", "string");
                                    }
                                });
                        put(
                                "description",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", "string");
                                    }
                                });
                    }
                });
        format.put("required", Arrays.asList("title", "description"));

        OllamaResult result = ollamaAPI.generate(OllamaGenerateRequestBuilder.builder().withModel(modelName)
                .withPrompt("What's in this image? Give me response as JSON fields named title and description.")
                .withImages(List.of(tempImageFile)).withFormat(format).build(), null);
        System.out.println(result.getResponse());
    }
}
