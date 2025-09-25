package io.github.ollama4j.examples.tools.toolspecs;

import io.github.ollama4j.tools.Tools;

import java.util.Map;

public class FuelPriceToolSpec {
    private FuelPriceToolSpec() { /* empty constructor */ }

    public static Tools.Tool getSpecification() {
        return
                Tools.Tool.builder()
                        .toolSpec(
                                Tools.ToolSpec.builder()
                                        .name("fuel-price-reporter")
                                        .description(
                                                "Gets the fuel for a given city for petrol of"
                                                        + " diesel.")
                                        .parameters(
                                                Tools.Parameters.of(
                                                        Map.of(
                                                                "city",
                                                                Tools.Property.builder()
                                                                        .type("string")
                                                                        .description(
                                                                                "The city to get"
                                                                                        + " the fuel"
                                                                                        + " price for.")
                                                                        .required(true)
                                                                        .build(),
                                                                "fuelType",
                                                                Tools.Property.builder()
                                                                        .type("string")
                                                                        .description(
                                                                                "The fuel type -"
                                                                                        + " either"
                                                                                        + " petrol or"
                                                                                        + " diesel.")
                                                                        .required(true)
                                                                        .build())))
                                        .build())
                        .toolFunction(
                                arguments -> {
                                    String location = arguments.get("city").toString();
                                    String fuelType = arguments.get("fuelType").toString();
                                    return "Price of "
                                            + fuelType
                                            + " in "
                                            + location
                                            + " is Rs.103/L.";
                                })
                        .build();
    }
}
