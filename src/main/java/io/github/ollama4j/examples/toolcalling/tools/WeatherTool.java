package io.github.ollama4j.examples.toolcalling.tools;

import java.util.Map;

public class WeatherTool {
    public static String getCurrentWeather(Map<String, Object> arguments) {
        // Get details from weather API
        String location = arguments.get("city").toString();
        return "Currently " + location + "'s weather is beautiful.";
    }
}
