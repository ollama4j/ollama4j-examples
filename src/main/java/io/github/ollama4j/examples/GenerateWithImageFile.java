package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.impl.ConsoleOutputGenerateTokenHandler;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
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

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "moondream:1.8b";
        ollama.pullModel(model);

        nonStreamingWithFile(ollama, model);
        streamingWithFile(ollama, model);
        nonStreamingWithFileAndFormat(ollama, model);
    }

    public static void nonStreamingWithFile(Ollama ollama, String modelName) throws Exception {
        // Load image from resources and copy to a temporary file
        InputStream is =
                GenerateWithImageFile.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(modelName)
                                .withPrompt("What's in this image?")
                                .withImages(List.of(tempImageFile))
                                .build(),
                        null);
        System.out.println(result.getResponse());
    }

    public static void streamingWithFile(Ollama ollama, String modelName) throws Exception {
        // Load image from resources and copy to a temporary file
        InputStream is =
                GenerateWithImageFile.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(modelName)
                                .withPrompt("What's in this image?")
                                .withImages(List.of(tempImageFile))
                                .build(),
                        new OllamaGenerateStreamObserver(
                                null, new ConsoleOutputGenerateTokenHandler()));
        System.out.println(result.getResponse());
    }

    public static void nonStreamingWithFileAndFormat(Ollama ollama, String modelName)
            throws Exception {
        // Load image from resources and copy to a temporary file
        InputStream is =
                GenerateWithImageFile.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
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
                                        put("type", String.class.getSimpleName().toLowerCase());
                                    }
                                });
                        put(
                                "description",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", String.class.getSimpleName().toLowerCase());
                                    }
                                });
                    }
                });
        format.put("required", Arrays.asList("title", "description"));

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(modelName)
                                .withPrompt(
                                        "What's in this image? Give me response as JSON fields"
                                                + " named title and description.")
                                .withImages(List.of(tempImageFile))
                                .withFormat(format)
                                .build(),
                        null);
        System.out.println(result.getResponse());
    }
}
