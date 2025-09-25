package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.tools.toolspecs.EmployeeFinderToolSpec;
import io.github.ollama4j.examples.tools.toolspecs.FuelPriceToolSpec;
import io.github.ollama4j.examples.tools.toolspecs.WeatherToolSpec;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.Utilities;

public class MultiToolRegistryExample {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        ollamaAPI.pullModel(model);

        // Get the references to the tool specifications
        Tools.Tool fuelPriceToolSpecification = FuelPriceToolSpec.getSpecification();
        Tools.Tool weatherToolSpecification = WeatherToolSpec.getSpecification();
        Tools.Tool employeeFinderToolSpecification = EmployeeFinderToolSpec.getSpecification();

        // Register the tool specifications
        ollamaAPI.registerTool(fuelPriceToolSpecification);
        ollamaAPI.registerTool(weatherToolSpecification);
        ollamaAPI.registerTool(employeeFinderToolSpecification);

        // Use the fuel-price tool specifications in the prompt
        OllamaResult res = ollamaAPI
                .generate(OllamaGenerateRequestBuilder.builder().withModel(model).withUseTools(true)
                        .withPrompt("How much does petrol cost in Bengaluru?").build(), null);

        // Use the weather tool specifications in the prompt
        OllamaResult res2 = ollamaAPI
                .generate(OllamaGenerateRequestBuilder.builder().withModel(model).withUseTools(true)
                        .withPrompt("What is the current weather in Bengaluru?").build(), null);

        // Use the database query tool specifications in the prompt
        OllamaResult res3 = ollamaAPI
                .generate(OllamaGenerateRequestBuilder.builder().withModel(model).withUseTools(true)
                        .withPrompt("Give me the details of the employee named Rahul Kumar and tell me if he would have like the weather in Bengaluru compared to his city and whether petrol is decently priced for him for his daily commute?")
                        .build(), null);

        System.out.println("[Response 1]: " + res.getResponse());
        System.out.println("[Response 2]: " + res2.getResponse());
        System.out.println("[Response 3]: " + res3.getResponse());
    }
}
