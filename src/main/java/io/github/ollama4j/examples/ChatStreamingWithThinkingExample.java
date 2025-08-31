package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.generate.OllamaStreamHandler;

@SuppressWarnings("DuplicatedCode")
public class ChatStreamingWithThinkingExample {

    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(120);
        ollamaAPI.setVerbose(false);

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gpt-oss:20b");

        OllamaChatRequest chatRequest = builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?").build();

        // Define a thinking stream handler
        OllamaStreamHandler thinkingStreamHandler = (s) -> {
            System.out.print(s.toUpperCase());
        };
        // Define a response stream handler
        OllamaStreamHandler responseStreamHandler = (s) -> {
            System.out.print(s.toLowerCase());
        };
        // pass the stream handlers to the chat method
        ollamaAPI.chat(chatRequest, thinkingStreamHandler, responseStreamHandler);
    }
}
