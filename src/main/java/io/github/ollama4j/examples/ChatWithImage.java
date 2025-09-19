package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.utils.Utilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ChatWithImage {

    public static void main(String[] args) throws Exception {

        String imageModel = "moondream:1.8b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(imageModel);

        // Load image from resources and copy to a temporary file
        InputStream is =
                ChatWithImage.class.getClassLoader().getResourceAsStream("dog-on-boat.jpg");
        if (is == null) {
            throw new FileNotFoundException("Image 'dog-on-boat.jpg' not found in resources!");
        }
        File tempImageFile = File.createTempFile("dog-on-boat", ".jpg");
        Files.copy(is, tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // First request: Ask about the image
        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "What's in the picture?",
                                null,
                                List.of(tempImageFile))
                        .build();

        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);
        System.out.println(
                "First answer: " + chatResult.getResponseModel().getMessage().getResponse());

        builder.reset();

        // Follow-up: Ask about the dog breed based on context
        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(OllamaChatMessageRole.USER, "What's the dog's breed?")
                        .build();

        chatResult = ollamaAPI.chat(requestModel, null);
        System.out.println(
                "Second answer: " + chatResult.getResponseModel().getMessage().getResponse());
    }
}
