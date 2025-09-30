package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.impl.ConsoleOutputGenerateTokenHandler;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.utils.Utilities;

public class ChatWithConsoleHandlerExample {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);
        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "List all cricket world cup teams of 2019. Name the teams!")
                        .build();

        // Define stream handlers.
        OllamaGenerateTokenHandler thinkingStreamHandler = new ConsoleOutputGenerateTokenHandler();
        OllamaGenerateTokenHandler responseStreamHandler = new ConsoleOutputGenerateTokenHandler();

        ollama.chat(
                requestModel,
                new OllamaChatStreamObserver(thinkingStreamHandler, responseStreamHandler));
    }
}
