package io.github.ollama4j.examples.toolcalling.tools;

import java.util.Map;

public class FuelPriceTool {
    public static String getCurrentFuelPrice(Map<String, Object> arguments) {
        // Get details from fuel price API
        String location = arguments.get("location").toString();
        String fuelType = arguments.get("fuelType").toString();
        return "Current price of " + fuelType + " in " + location + " is Rs.103/L";
    }
}
