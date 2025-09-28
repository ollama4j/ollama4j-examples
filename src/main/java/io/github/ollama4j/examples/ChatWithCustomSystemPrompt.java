package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.utils.Utilities;

public class ChatWithCustomSystemPrompt {

    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);

        // create request with system-prompt (overriding the model defaults) and user question
        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.SYSTEM,
                                "You are a silent bot that only says 'Shhh!'. DO NOT say anything"
                                        + " else under any circumstances!")
                        .withMessage(
                                OllamaChatMessageRole.USER,
                                "What is the capital of France? And what's France's connection with"
                                        + " Mona Lisa?")
                        .build();

        // start conversation with model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);

        System.out.println(chatResult.getResponseModel().getMessage().getResponse());
    }
}
