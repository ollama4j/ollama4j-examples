package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.tools.annotated.GlobalConstantGenerator;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.tools.annotations.OllamaToolService;
import io.github.ollama4j.utils.Utilities;

@OllamaToolService(providers = GlobalConstantGenerator.class)
public class AnnotatedToolCallingExample {
//
//    public static void main(String[] args) throws Exception {
//        String model = "mistral:7b";
//
//        OllamaAPI ollamaAPI = Utilities.setUp();
//        ollamaAPI.pullModel(model);
//
//        // Inject the annotated method to the ollama tools-registry
//        ollamaAPI.registerAnnotatedTools();
//
//        // Alternatively, register a specific tool instance with annotated methods
//        // ollamaAPI.registerAnnotatedTools(new GlobalConstantGenerator());
//
//        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);
//        OllamaChatRequest requestModel =
//                builder.withMessage(
//                                OllamaChatMessageRole.USER,
//                                "Compute the most important constant in the world using 10 digits.")
//                        .build();
//
//        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);
//        System.out.println(
//                "First answer: " + chatResult.getResponseModel().getMessage().getResponse());
//
//        requestModel =
//                builder.withMessages(chatResult.getChatHistory())
//                        .withMessage(
//                                OllamaChatMessageRole.USER,
//                                "Compute another most important constant in the world using 3"
//                                        + " digits.")
//                        .build();
//
//        chatResult = ollamaAPI.chat(requestModel, null);
//        System.out.println(
//                "Second answer: " + chatResult.getResponseModel().getMessage().getResponse());
//    }
}
