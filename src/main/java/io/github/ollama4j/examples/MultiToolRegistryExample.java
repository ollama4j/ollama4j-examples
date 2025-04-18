package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.toolspecs.DatabaseQueryToolSpec;
import io.github.ollama4j.examples.toolcalling.toolspecs.FuelPriceToolSpec;
import io.github.ollama4j.examples.toolcalling.toolspecs.WeatherToolSpec;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

public class MultiToolRegistryExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");
        String model = Utilities.getFromConfig("tools_model_mistral");

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);
        ollamaAPI.setRequestTimeoutSeconds(60);

        // Get the references to the tool specifications
        Tools.ToolSpecification fuelPriceToolSpecification = FuelPriceToolSpec.getSpecification();
        Tools.ToolSpecification weatherToolSpecification = WeatherToolSpec.getSpecification();
        Tools.ToolSpecification databaseQueryToolSpecification = DatabaseQueryToolSpec.getSpecification();

        // Register the tool specifications
        ollamaAPI.registerTool(fuelPriceToolSpecification);
        ollamaAPI.registerTool(weatherToolSpecification);
        ollamaAPI.registerTool(databaseQueryToolSpecification);

        // Use the fuel-price tool specifications in the prompt
        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(model, new Tools.PromptBuilder()
                .withToolSpecification(fuelPriceToolSpecification)
                .withPrompt("What is the petrol price in Bengaluru?")
                .build(), new OptionsBuilder().build()).getToolResults()) {
            System.out.printf("[Result of executing tool '%s']: %s%n", r.getFunctionName(), r.getResult().toString());
        }

        // Use the weather tool specifications in the prompt
        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(model, new Tools.PromptBuilder()
                .withToolSpecification(weatherToolSpecification)
                .withPrompt("What is the current weather in Bengaluru?")
                .build(), new OptionsBuilder().build()).getToolResults()) {
            System.out.printf("[Result of executing tool '%s']: %s%n", r.getFunctionName(), r.getResult().toString());
        }

        // Use the database query tool specifications in the prompt
        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(model, new Tools.PromptBuilder()
                .withToolSpecification(databaseQueryToolSpecification)
                .withPrompt("Give me the details of the employee named 'Rahul Kumar'?")
                .build(), new OptionsBuilder().build()).getToolResults()) {
            System.out.printf("[Result of executing tool '%s']: %s%n", r.getFunctionName(), r.getResult().toString());
        }
    }
}