package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
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

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "moondream:1.8b";
        ollama.pullModel(model);

        OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);

        // Load image from resources and copy to a temporary file - or load from your own file path
        // directly
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

        OllamaChatResult chatResult = ollama.chat(requestModel, null);
        System.out.println(
                "First answer: " + chatResult.getResponseModel().getMessage().getResponse());

        builder.reset();

        // Follow-up: Ask about the dog breed based on context
        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(OllamaChatMessageRole.USER, "What's the dog's breed?")
                        .build();

        chatResult = ollama.chat(requestModel, null);
        System.out.println(
                "Second answer: " + chatResult.getResponseModel().getMessage().getResponse());
    }
}
