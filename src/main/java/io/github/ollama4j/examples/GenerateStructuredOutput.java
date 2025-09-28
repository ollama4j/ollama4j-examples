package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Utilities;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GenerateStructuredOutput {

    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "qwen3:0.6b";
        ollama.pullModel(model);

        String prompt =
                "Batman is thirty years old and is busy saving Gotham. Respond using a formatted"
                    + " and prettified JSON in the given format only. Be very precise about picking"
                    + " the values.";
        Map<String, Object> ageOfPerson = new HashMap<>();
        ageOfPerson.put("type", Integer.class.getSimpleName().toLowerCase());

        Map<String, Object> heroName = new HashMap<>();
        heroName.put("type", String.class.getSimpleName().toLowerCase());

        Map<String, Object> properties = new HashMap<>();
        properties.put("ageOfTheHero", ageOfPerson);
        properties.put("heroName", heroName);

        Map<String, Object> format = new HashMap<>();
        format.put("type", "object");
        format.put("properties", properties);
        format.put("required", Arrays.asList("ageOfPerson", "heroName"));

        OllamaResult result =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withPrompt(prompt)
                                .withFormat(format)
                                .withThink(false)
                                .build(),
                        null);
        System.out.println(result.getResponse());
    }
}
