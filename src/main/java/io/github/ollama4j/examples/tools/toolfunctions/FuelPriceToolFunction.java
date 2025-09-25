package io.github.ollama4j.examples.tools.toolfunctions;

import java.util.Map;

public class FuelPriceToolFunction {
    private FuelPriceToolFunction() { /* empty constructor */ }

    public static String getCurrentFuelPrice(Map<String, Object> arguments) {
        // Get details from fuel price API
        if (arguments.size() != 2) {
            return "Not enough data!";
        }
        String location = arguments.get("city").toString();
        String fuelType = arguments.get("fuelType").toString();
        return "Current price of " + fuelType + " in " + location + " is Rs.103/L";
    }
}
