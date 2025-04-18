package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StructuredOutputMappedToObject {

    public static void main(String[] args) throws Exception {
        String host = "http://localhost:11434/";
        String chatModel = "qwen2.5:0.5b";

        OllamaAPI api = new OllamaAPI(host);

        int age = 28;
        boolean available = false;

        String prompt = "Batman is " + age + " years old and is " + (available ? "available" : "not available")
                + " because he is busy saving Gotham City. Respond using JSON";

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

        Person person = result.as(Person.class);
        System.out.println(person);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    private int age;
    private boolean available;
}