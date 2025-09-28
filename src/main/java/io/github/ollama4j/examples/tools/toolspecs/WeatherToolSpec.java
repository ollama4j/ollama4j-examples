package io.github.ollama4j.examples.tools.toolspecs;

import io.github.ollama4j.tools.Tools;
import java.util.Map;

public class WeatherToolSpec {
    private WeatherToolSpec() {
        /* empty constructor */
    }

    public static Tools.Tool getSpecification() {
        return Tools.Tool.builder()
                .toolSpec(
                        Tools.ToolSpec.builder()
                                .name("weather-reporter")
                                .description("Gets the weather for a given city")
                                .parameters(
                                        Tools.Parameters.of(
                                                Map.of(
                                                        "city",
                                                        Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The city to get"
                                                                                + " the weather"
                                                                                + " details"
                                                                                + " for.")
                                                                .required(true)
                                                                .build())))
                                .build())
                .toolFunction(
                        arguments -> {
                            String location = arguments.get("city").toString();
                            return "Currently " + location + "'s weather is beautiful.";
                        })
                .build();
    }
}
