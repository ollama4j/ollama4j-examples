package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;

public class ChatExample {

    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gemma3:270m");

        // create first user question
        OllamaChatRequest requestModel = builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                .build();

        // start conversation with model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel,null);

        System.out.println("First answer: " + chatResult.getResponseModel().getMessage().getContent());

        // create next userQuestion
        requestModel = builder.withMessages(chatResult.getChatHistory()).withMessage(OllamaChatMessageRole.USER, "And what is the second largest city?").build();

        // "continue" conversation with model
        chatResult = ollamaAPI.chat(requestModel,null);

        System.out.println("Second answer: " + chatResult.getResponseModel().getMessage().getContent());

        System.out.println("Chat History: " + chatResult.getChatHistory());
    }
}
