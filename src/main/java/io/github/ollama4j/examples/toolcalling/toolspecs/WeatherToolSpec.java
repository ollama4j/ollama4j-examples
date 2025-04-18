package io.github.ollama4j.examples.toolcalling.toolspecs;

import io.github.ollama4j.examples.toolcalling.tools.FuelPriceTool;
import io.github.ollama4j.examples.toolcalling.tools.WeatherTool;
import io.github.ollama4j.tools.Tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WeatherToolSpec {

    public static Tools.ToolSpecification getSpecification() {
        return Tools.ToolSpecification.builder()
                .functionName("current-weather")
                .functionDescription("Get current weather")
                .toolFunction(WeatherTool::getCurrentWeather)
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
