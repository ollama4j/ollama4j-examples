package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.impl.ConsoleOutputGenerateTokenHandler;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
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

public class GenerateWithImageFileSimple {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "moondream:1.8b";
        ollama.pullModel(model);

        // Load image from resources and copy to a temporary file - or load from your own file path directly
        InputStream is =
                GenerateWithImageFileSimple.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequest.builder()
                                .withModel(model)
                                .withPrompt("What's in this image?")
                                .withImages(List.of(tempImageFile))
                                .build(),
                        null);
        System.out.println(result.getResponse());
    }
}
