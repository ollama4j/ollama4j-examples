package io.github.ollama4j.examples.toolcalling.toolspecs;

import io.github.ollama4j.examples.toolcalling.tools.FuelPriceTool;
import io.github.ollama4j.tools.Tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FuelPriceToolSpec {

    public static Tools.ToolSpecification getSpecification() {
        return Tools.ToolSpecification.builder()
                .functionName("current-fuel-price")
                .functionDescription("Get current fuel price")
                .toolFunction(FuelPriceTool::getCurrentFuelPrice)
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
                                                                .required(List.of("location", "fuelType"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
