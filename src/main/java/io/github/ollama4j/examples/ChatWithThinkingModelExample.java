package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.utils.Utilities;

public class ChatWithThinkingModelExample {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);

        // create first user question
        OllamaChatRequest requestModel =
                builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                        .withThinking(true)
                        .build();

        // start conversation with model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);

        System.out.println(
                "First thinking response: "
                        + chatResult.getResponseModel().getMessage().getThinking());
        System.out.println(
                "First answer response: "
                        + chatResult.getResponseModel().getMessage().getResponse());
        // create next userQuestion
        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(
                                OllamaChatMessageRole.USER, "And what is the second largest city?")
                        .withThinking(true)
                        .build();

        // "continue" conversation with model
        chatResult = ollamaAPI.chat(requestModel, null);

        System.out.println(
                "Second thinking response: "
                        + chatResult.getResponseModel().getMessage().getThinking());
        System.out.println(
                "Second answer response: "
                        + chatResult.getResponseModel().getMessage().getResponse());

        System.out.println("Chat History: " + chatResult.getChatHistory());
    }
}
