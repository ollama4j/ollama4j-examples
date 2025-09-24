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

        String model = "mistral:7b";

        OllamaAPI api = Utilities.setUp();

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
                                "ageOfPerson",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", "number");
                                    }
                                });
                        put(
                                "heroName",
                                new HashMap<String, Object>() {
                                    {
                                        put("type", "string");
                                    }
                                });
                    }
                });
        format.put("required", Arrays.asList("ageOfPerson", "heroName"));

        OllamaResult result = api.generate(OllamaGenerateRequestBuilder.builder().withModel(model).withPrompt(prompt).withFormat(format).build(), null);

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
    private String heroName;
    private String
            ageOfPerson; // using string here as the model can spit out wierd value for age. It does
    // not always return a number :)
}
