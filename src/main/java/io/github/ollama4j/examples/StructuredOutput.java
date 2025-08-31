package io.github.ollama4j.examples;


import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import lombok.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StructuredOutput {

    public static void main(String[] args) throws Exception {
        String host = "http://192.168.29.223:11434/";
        String model = "mistral:7b";

        OllamaAPI api = new OllamaAPI(host);
        api.setRequestTimeoutSeconds(120);
        api.pullModel(model);

        String prompt = "Batman is thirty years old and is busy saving Gotham. Respond using JSON in the given format only. Be very precise about picking the values.";
        Map<String, Object> format = new HashMap<>();
        format.put("type", "object");
        format.put("properties", new HashMap<String, Object>() {
            {
                put("ageOfPerson", new HashMap<String, Object>() {
                    {
                        put("type", "number");
                    }
                });
                put("heroName", new HashMap<String, Object>() {
                    {
                        put("type", "string");
                    }
                });
            }
        });
        format.put("required", Arrays.asList("ageOfPerson", "heroName"));

        OllamaResult result = api.generate(model, prompt, format);
        System.out.println(result.as(HeroInfo.class));
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class HeroInfo {
    private String heroName;
    private int ageOfPerson;
}