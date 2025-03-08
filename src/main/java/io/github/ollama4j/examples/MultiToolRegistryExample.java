package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
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

        Tools.ToolSpecification fuelPriceToolSpecification = getFuelPriceToolSpecification();
        Tools.ToolSpecification weatherToolSpecification = getWeatherToolSpecification();

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

    private static Tools.ToolSpecification getFuelPriceToolSpecification() {
        return Tools.ToolSpecification.builder()
                .functionName("current-fuel-price")
                .functionDescription("Get current fuel price")
                .toolFunction(SampleTools::getCurrentFuelPrice)
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("prompt")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-location-fuel-info")
                                                .description("Get location and fuel type details")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters.builder()
                                                                .type("object")
                                                                .properties(
                                                                        Map.of(
                                                                                "location", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The city, e.g. New Delhi, India")
                                                                                        .required(true)
                                                                                        .build(),
                                                                                "fuelType", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The fuel type.")
                                                                                        .enumValues(Arrays.asList("petrol", "diesel"))
                                                                                        .required(true)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .required(java.util.List.of("location", "fuelType"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                ).build();
    }

    private static Tools.ToolSpecification getWeatherToolSpecification() {
        return Tools.ToolSpecification.builder()
                .functionName("current-weather")
                .functionDescription("Get current weather")
                .toolFunction(SampleTools::getCurrentWeather)
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("prompt")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-location-weather-info")
                                                .description("Get location details")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters.builder()
                                                                .type("object")
                                                                .properties(
                                                                        Map.of(
                                                                                "city", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The city, e.g. New Delhi, India")
                                                                                        .required(true)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .required(java.util.List.of("city"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                ).build();
    }
}


class SampleTools {
    public static String getCurrentFuelPrice(Map<String, Object> arguments) {
        // Get details from fuel price API
        String location = arguments.get("location").toString();
        String fuelType = arguments.get("fuelType").toString();
        return "Current price of " + fuelType + " in " + location + " is Rs.103/L";
    }

    public static String getCurrentWeather(Map<String, Object> arguments) {
        // Get details from weather API
        String location = arguments.get("city").toString();
        return "Currently " + location + "'s weather is beautiful.";
    }
}