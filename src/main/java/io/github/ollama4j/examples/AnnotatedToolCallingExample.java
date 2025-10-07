package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.examples.tools.annotated.GlobalConstantGenerator;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.tools.annotations.OllamaToolService;
import io.github.ollama4j.utils.Utilities;

@OllamaToolService(providers = GlobalConstantGenerator.class)
public class AnnotatedToolCallingExample {

    public static void main(String[] args) throws Exception {
        String model = "mistral:7b";

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        ollama.pullModel(model);

        // Inject the annotated method to the ollama tools-registry
        ollama.registerAnnotatedTools();

        // Alternatively, register a specific tool instance with annotated methods
        // ollama.registerAnnotatedTools(new GlobalConstantGenerator());

        OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);
        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "Compute the most important constant in the world using 10 digits.")
                        .build();

        OllamaChatResult chatResult = ollama.chat(requestModel, null);
        System.out.println(
                "First answer: " + chatResult.getResponseModel().getMessage().getResponse());

        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(
                                OllamaChatMessageRole.USER,
                                "Compute another most important constant in the world using 3"
                                        + " digits.")
                        .build();

        chatResult = ollama.chat(requestModel, null);
        System.out.println(
                "Second answer: " + chatResult.getResponseModel().getMessage().getResponse());
    }
}
