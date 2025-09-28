package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.*;

public class GenerateStructuredOutputMappedToObject {

    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "qwen3:0.6b";
        ollamaAPI.pullModel(model);

        String prompt =
                "Batman is thirty years old and is busy saving Gotham. Respond using JSON in the"
                        + " given format only. Be very precise about picking the values.";

        Map<String, Object> format = new HashMap<>();
        format.put("type", "object");
        format.put(
                "properties",
                new HashMap<String, Object>() {
                    {
                        put(
                                "age",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", Integer.class.getSimpleName().toLowerCase());
                                    }
                                });
                        put(
                                "name",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", String.class.getSimpleName().toLowerCase());
                                    }
                                });
                    }
                });
        format.put("required", Arrays.asList("age", "name"));

        OllamaResult result =
                ollamaAPI.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withPrompt(prompt)
                                .withFormat(format)
                                .build(),
                        null);

        HeroInfo hero = result.as(HeroInfo.class);
        System.out.println(hero);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class HeroInfo {
    private String name;
    private String age; // using string here as the model can spit out wierd value for age. It does
    // not always return a number :)
}
