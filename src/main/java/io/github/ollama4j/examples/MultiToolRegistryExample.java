package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.examples.toolcalling.tools.FuelPriceTool;
import io.github.ollama4j.examples.toolcalling.tools.WeatherTool;
import io.github.ollama4j.examples.toolcalling.toolspecs.FuelPriceToolSpec;
import io.github.ollama4j.examples.toolcalling.toolspecs.WeatherToolSpec;
import io.github.ollama4j.utils.Utilities;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;

import java.util.Arrays;
import java.util.Map;

public class MultiToolRegistryExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");
        String model = Utilities.getFromConfig("tools_model_mistral");

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);
        ollamaAPI.setRequestTimeoutSeconds(60);

        Tools.ToolSpecification fuelPriceToolSpecification = FuelPriceToolSpec.getSpecification();
        Tools.ToolSpecification weatherToolSpecification = WeatherToolSpec.getSpecification();

        ollamaAPI.registerTool(fuelPriceToolSpecification);
        ollamaAPI.registerTool(weatherToolSpecification);

        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(model, new Tools.PromptBuilder()
                .withToolSpecification(fuelPriceToolSpecification)
                .withPrompt("What is the petrol price in Bengaluru?")
                .build(), new OptionsBuilder().build()).getToolResults()) {
            System.out.printf("[Result of executing tool '%s']: %s%n", r.getFunctionName(), r.getResult().toString());
        }

        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(model, new Tools.PromptBuilder()
                .withToolSpecification(weatherToolSpecification)
                .withPrompt("What is the current weather in Bengaluru?")
                .build(), new OptionsBuilder().build()).getToolResults()) {
            System.out.printf("[Result of executing tool '%s']: %s%n", r.getFunctionName(), r.getResult().toString());
        }
    }

}