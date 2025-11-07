package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.utils.Utilities;

public class ChatWithThinkingModelExample {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "qwen3:0.6b";
        ollama.pullModel(model);
        OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);

        // create first user question
        OllamaChatRequest requestModel =
                builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                        .withThinking(ThinkMode.ENABLED)
                        .build();

        // start conversation with model
        OllamaChatResult chatResult = ollama.chat(requestModel, null);

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
                        .withThinking(ThinkMode.ENABLED)
                        .build();

        // "continue" conversation with model
        chatResult = ollama.chat(requestModel, null);

        System.out.println(
                "Second thinking response: "
                        + chatResult.getResponseModel().getMessage().getThinking());
        System.out.println(
                "Second answer response: "
                        + chatResult.getResponseModel().getMessage().getResponse());

        System.out.println("Chat History: " + chatResult.getChatHistory());
    }
}
