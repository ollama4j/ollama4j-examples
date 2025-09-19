package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.toolspecs.DatabaseQueryToolSpec;
import io.github.ollama4j.examples.toolcalling.toolspecs.FuelPriceToolSpec;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.tools.sampletools.WeatherTool;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

public class MultiToolRegistryExample {
    public static void main(String[] args) throws Exception {

        String model = "mistral:7b";

        OllamaAPI ollamaAPI = Utilities.setUp();

        // Get the references to the tool specifications
        Tools.ToolSpecification fuelPriceToolSpecification = FuelPriceToolSpec.getSpecification();
        Tools.ToolSpecification weatherToolSpecification = new WeatherTool().getSpecification();
        Tools.ToolSpecification databaseQueryToolSpecification =
                DatabaseQueryToolSpec.getSpecification();

        // Register the tool specifications
        ollamaAPI.registerTool(fuelPriceToolSpecification);
        ollamaAPI.registerTool(weatherToolSpecification);
        ollamaAPI.registerTool(databaseQueryToolSpecification);

        Options options = new OptionsBuilder().build();

        // Use the fuel-price tool specifications in the prompt
        for (OllamaToolsResult.ToolResult r :
                ollamaAPI
                        .generateWithTools(
                                model,
                                new Tools.PromptBuilder()
                                        .withToolSpecification(fuelPriceToolSpecification)
                                        .withPrompt("What is the petrol price in Bengaluru?")
                                        .build(),
                                options,
                                null)
                        .getToolResults()) {
            System.out.printf(
                    "[Result of executing tool '%s']: %s%n",
                    r.getFunctionName(), r.getResult().toString());
        }

        // Use the weather tool specifications in the prompt
        for (OllamaToolsResult.ToolResult r :
                ollamaAPI
                        .generateWithTools(
                                model,
                                new Tools.PromptBuilder()
                                        .withToolSpecification(weatherToolSpecification)
                                        .withPrompt("What is the current weather in Bengaluru?")
                                        .build(),
                                options,
                                null)
                        .getToolResults()) {
            System.out.printf(
                    "[Result of executing tool '%s']: %s%n",
                    r.getFunctionName(), r.getResult().toString());
        }

        // Use the database query tool specifications in the prompt
        for (OllamaToolsResult.ToolResult r :
                ollamaAPI
                        .generateWithTools(
                                model,
                                new Tools.PromptBuilder()
                                        .withToolSpecification(databaseQueryToolSpecification)
                                        .withPrompt(
                                                "Give me the details of the employee named 'Rahul"
                                                        + " Kumar'?")
                                        .build(),
                                options,
                                null)
                        .getToolResults()) {
            System.out.printf(
                    "[Result of executing tool '%s']: %s%n",
                    r.getFunctionName(), r.getResult().toString());
        }
    }
}
