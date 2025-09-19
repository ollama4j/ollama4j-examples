package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.toolspecs.DatabaseQueryToolSpec;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.Utilities;

public class ChatWithTools {
    public static void main(String[] args) throws Exception {

        String modelName = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(modelName);

        final Tools.ToolSpecification databaseQueryToolSpecification =
                DatabaseQueryToolSpec.getSpecification();

        ollamaAPI.registerTool(databaseQueryToolSpecification);

        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "Give me the ID of the employee named 'Rahul Kumar'?")
                        .build();

        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);
        System.out.println(
                "First answer: " + chatResult.getResponseModel().getMessage().getResponse());

        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(OllamaChatMessageRole.USER, "What's his last name?")
                        .build();

        chatResult = ollamaAPI.chat(requestModel, null);
        System.out.println(
                "Second answer: " + chatResult.getResponseModel().getMessage().getResponse());
    }
}
