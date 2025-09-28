package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.examples.tools.toolspecs.EmployeeFinderToolSpec;
import io.github.ollama4j.examples.tools.toolspecs.FuelPriceToolSpec;
import io.github.ollama4j.examples.tools.toolspecs.WeatherToolSpec;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.Utilities;

public class MultiToolRegistryExample {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new OllamaAPI("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        ollama.pullModel(model);

        // Get the references to the tool specifications
        Tools.Tool fuelPriceToolSpecification = FuelPriceToolSpec.getSpecification();
        Tools.Tool weatherToolSpecification = WeatherToolSpec.getSpecification();
        Tools.Tool employeeFinderToolSpecification = EmployeeFinderToolSpec.getSpecification();

        // Register the tool specifications
        ollama.registerTool(fuelPriceToolSpecification);
        ollama.registerTool(weatherToolSpecification);
        ollama.registerTool(employeeFinderToolSpecification);

        // Use the fuel-price tool specifications in the prompt
        OllamaResult res =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withUseTools(true)
                                .withPrompt("How much does petrol cost in Bengaluru?")
                                .build(),
                        null);

        // Use the weather tool specifications in the prompt
        OllamaResult res2 =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withUseTools(true)
                                .withPrompt("What is the current weather in Bengaluru?")
                                .build(),
                        null);

        // Use the database query tool specifications in the prompt
        OllamaResult res3 =
                ollama.generate(
                        OllamaGenerateRequestBuilder.builder()
                                .withModel(model)
                                .withUseTools(true)
                                .withPrompt(
                                        "Give me the details of the employee named Rahul Kumar and"
                                            + " tell me if he would have like the weather in"
                                            + " Bengaluru compared to his city and whether petrol"
                                            + " is decently priced for him for his daily commute?")
                                .build(),
                        null);

        System.out.println("[Response 1]: " + res.getResponse());
        System.out.println("[Response 2]: " + res2.getResponse());
        System.out.println("[Response 3]: " + res3.getResponse());
    }
}
