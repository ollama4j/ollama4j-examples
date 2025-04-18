package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.toolspecs.DatabaseQueryToolSpec;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.Utilities;

import java.io.IOException;

public class ChatWithTools {
    public static void main(String[] args) throws ToolInvocationException, OllamaBaseException, IOException, InterruptedException {
        String host = Utilities.getFromConfig("host");
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(60);
        ollamaAPI.setVerbose(false);

        String modelName = Utilities.getFromConfig("tools_model_mistral");
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance(modelName);

        final Tools.ToolSpecification databaseQueryToolSpecification = DatabaseQueryToolSpec.getSpecification();

        ollamaAPI.registerTool(databaseQueryToolSpecification);

        OllamaChatRequest requestModel = builder
                .withMessage(OllamaChatMessageRole.USER,
                        "Give me the ID of the employee named 'Rahul Kumar'?")
                .build();

        OllamaChatResult chatResult = ollamaAPI.chat(requestModel);
        System.out.println("First answer: " + chatResult.getResponseModel().getMessage().getContent());

        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(OllamaChatMessageRole.USER, "What's the last name?").build();

        chatResult = ollamaAPI.chat(requestModel);
        System.out.println("Second answer: " + chatResult.getResponseModel().getMessage().getContent());
    }
}
