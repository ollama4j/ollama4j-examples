package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.models.generate.OllamaStreamHandler;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;

public class NewStructure {
    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(120);

//        OllamaResult ollamaResult = ollamaAPI.generate("gemma3:270m", "WHo are you", false, new OptionsBuilder().build(), System.out::print);

//        generateStreaming(ollamaAPI);
//        generateStreamingThinking(ollamaAPI);
//        generateAsync(ollamaAPI);
//        generate(ollamaAPI);
        chat(ollamaAPI);
//        chatStreaming(ollamaAPI);
    }

    public static void generateAsync(OllamaAPI ollamaAPI) throws OllamaBaseException, IOException, InterruptedException {
        OllamaAsyncResultStreamer resultStreamer = ollamaAPI.generateAsync("gpt-oss:20b", "Who are you", false, true);
        int pollIntervalMilliseconds = 1000;
        while (true) {
            String thinkingTokens = resultStreamer.getThinkingResponseStream().poll();
            String responseTokens = resultStreamer.getResponseStream().poll();
            System.out.print(thinkingTokens.toUpperCase());
            System.out.print(responseTokens.toLowerCase());
            Thread.sleep(pollIntervalMilliseconds);
            if (!resultStreamer.isAlive()) break;
        }
        System.out.println("Complete thinking response: " + resultStreamer.getCompleteThinkingResponse());
        System.out.println("Complete response: " + resultStreamer.getCompleteResponse());
    }

    public static void chat(OllamaAPI ollamaAPI) throws Exception {
//        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gemma3:270m");
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gpt-oss:20b");

        // create first user question
        OllamaChatRequest requestModel = builder.withMessage(OllamaChatMessageRole.USER, "Tell me a small story")
                .build();
        // start conversation with model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel);
        System.out.println("First thinking: " + chatResult.getResponseModel().getMessage().getThinking().toUpperCase());
        System.out.println("First answer: " + chatResult.getResponseModel().getMessage().getContent().toLowerCase());
        // create next userQuestion
        requestModel = builder.withMessages(chatResult.getChatHistory()).withMessage(OllamaChatMessageRole.USER, "And tell me more").build();
        // "continue" conversation with model
        chatResult = ollamaAPI.chat(requestModel);
        System.out.println("Second thinking: " + chatResult.getResponseModel().getMessage().getThinking().toUpperCase());
        System.out.println("Second answer: " + chatResult.getResponseModel().getMessage().getContent().toLowerCase());
        System.out.println("Chat History: " + chatResult.getChatHistory());
    }

    public static void chatStreaming(OllamaAPI ollamaAPI) throws Exception {
//        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gemma3:270m");
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("gpt-oss:20b");

        OllamaChatRequest chatRequest = builder.withMessage(OllamaChatMessageRole.USER, "What is the capital of France?")
                .build();

        // Define a stream handler.
        // Note: This handler will receive each token and concatenates them with the previously received tokens and prints the concatenated string
        OllamaStreamHandler thinkingStreamHandler = (s) -> {
            System.out.print(s.toUpperCase());
        };
        OllamaStreamHandler responseStreamHandler = (s) -> {
            System.out.print(s.toLowerCase());
        };

        // pass the stream handler to the chat method
        OllamaChatResult res = ollamaAPI.chat(chatRequest, thinkingStreamHandler, responseStreamHandler);
        System.out.println("\n\n[Full response]: " + res.getResponseModel().getMessage().getContent());
    }

    public static void generate(OllamaAPI ollamaAPI) throws OllamaBaseException, IOException, InterruptedException {
        String model = "gemma3:270m";
//        String model = "gpt-oss:20b";
        OllamaResult ollamaResult = ollamaAPI.generate(model, "Who are you", false, false, new OptionsBuilder().build());
//        System.out.println("Thinking: " + ollamaResult.getThinking());
//        System.out.println("Response: " + ollamaResult.getResponse());
        System.out.println(ollamaResult);
    }

    public static void generateStreaming(OllamaAPI ollamaAPI) throws OllamaBaseException, IOException, InterruptedException {
        OllamaResult ollamaResult = ollamaAPI.generate("gpt-oss:20b", "Who are you", false, new OptionsBuilder().build(), (responseToken) -> System.out.print(responseToken.toLowerCase()));
        System.out.println("Thinking: " + ollamaResult.getThinking());
        System.out.println("Response: " + ollamaResult.getResponse());
    }

    public static void generateStreamingThinking(OllamaAPI ollamaAPI) throws OllamaBaseException, IOException, InterruptedException {
        OllamaResult ollamaResult = ollamaAPI.generate("gpt-oss:20b", "Who are you", false, new OptionsBuilder().build(), (thinkingToken) -> System.out.print(thinkingToken.toUpperCase()), (responseToken) -> System.out.print(responseToken.toLowerCase()));
        System.out.println("Thinking: " + ollamaResult.getThinking());
        System.out.println("Response: " + ollamaResult.getResponse());
    }
}
