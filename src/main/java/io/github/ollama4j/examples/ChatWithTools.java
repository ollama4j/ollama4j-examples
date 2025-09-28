package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.tools.toolspecs.EmployeeFinderToolSpec;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.Utilities;

public class ChatWithTools {
    public static void main(String[] args) throws Exception {

        OllamaAPI ollamaAPI = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.builder().withModel(model);

        final Tools.Tool employeeFinderToolSpecification =
                EmployeeFinderToolSpec.getSpecification();

        ollamaAPI.registerTool(employeeFinderToolSpecification);

        OllamaChatRequest requestModel =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "Give me the ID of the employee named Rahul Kumar?")
                        .build();

        OllamaChatResult chatResult = ollamaAPI.chat(requestModel, null);
        System.out.println(
                "First answer: " + chatResult.getResponseModel().getMessage().getResponse());

        requestModel =
                builder.withMessages(chatResult.getChatHistory())
                        .withMessage(
                                OllamaChatMessageRole.USER, "What's his address and phone number?")
                        .build();

        chatResult = ollamaAPI.chat(requestModel, null);
        System.out.println(
                "Second answer: " + chatResult.getResponseModel().getMessage().getResponse());
    }
}
