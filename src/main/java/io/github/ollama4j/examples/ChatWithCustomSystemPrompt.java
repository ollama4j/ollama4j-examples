package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;

import java.io.IOException;


public class ChatWithCustomSystemPrompt {

    public static void main(String[] args) throws ToolInvocationException, OllamaBaseException, IOException, InterruptedException {
        String host = "http://192.168.29.223:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);


        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("qwen3:0.6b");

        // create request with system-prompt (overriding the model defaults) and user question
        OllamaChatRequest requestModel = builder
                .withMessage(OllamaChatMessageRole.SYSTEM, "You are a silent bot that only says 'Shhh!'. DO NOT say anything else under any circumstances!")
                .withMessage(OllamaChatMessageRole.USER, "What is the capital of France? And what's France's connection with Mona Lisa?")
                .build();

        // start conversation with model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);

        System.out.println(chatResult.getResponseModel().getMessage().getContent());
    }
}
