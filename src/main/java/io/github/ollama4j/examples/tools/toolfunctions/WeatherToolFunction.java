package io.github.ollama4j.examples.tools.toolfunctions;

import java.util.Map;

public class WeatherToolFunction {
    private WeatherToolFunction() { /* empty constructor */ }

    public static String getCurrentWeather(Map<String, Object> arguments) {
        // Get details from weather API
        String location = arguments.get("city").toString();
        return "Currently " + location + "'s weather is beautiful.";
    }
}
