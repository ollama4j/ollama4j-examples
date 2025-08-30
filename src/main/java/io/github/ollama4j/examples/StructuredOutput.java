package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StructuredOutput {

    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("OLLAMA_HOST");
        String model = Utilities.getFromConfig("TOOLS_MODEL");

        OllamaAPI api = new OllamaAPI(host);

        String chatModel = model;
        api.pullModel(chatModel);

        String prompt = "Ollama is 22 years old and is busy saving the world. Respond using JSON";
        Map<String, Object> format = new HashMap<>();
        format.put("type", "object");
        format.put("properties", new HashMap<String, Object>() {
            {
                put("age", new HashMap<String, Object>() {
                    {
                        put("type", "integer");
                    }
                });
                put("available", new HashMap<String, Object>() {
                    {
                        put("type", "boolean");
                    }
                });
            }
        });
        format.put("required", Arrays.asList("age", "available"));

        OllamaResult result = api.generate(chatModel, prompt, format);
        System.out.println(result.getResponse());
    }
}