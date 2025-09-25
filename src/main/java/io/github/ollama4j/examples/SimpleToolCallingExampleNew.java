/*
 * Ollama4j - Java library for interacting with Ollama server.
 * Copyright (c) 2025 Amith Koujalgi and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 *
*/
package io.github.ollama4j;

import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleToolCallingExample {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = new OllamaAPI("http://192.168.29.229:11434");
        String model = "mistral:7b";
        askModel(ollamaAPI, model);
    }

    public static void askModel(OllamaAPI ollamaAPI, String modelName) throws OllamaBaseException {
        ollamaAPI.pullModel(modelName);

        String prompt = "How is the weather in Bengaluru";

        Tools.Tool weatherTool =
                Tools.Tool.builder()
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
        Tools.Tool fuelPriceTool =
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
                                    return "Currently " + location + "'s weather is beautiful.";
                                })
                        .build();
        List<Tools.Tool> tools = new ArrayList<>();
        tools.add(weatherTool);
        tools.add(fuelPriceTool);
        OllamaGenerateRequest request =
                OllamaGenerateRequestBuilder.builder()
                        .withModel(modelName)
                        .withPrompt(prompt)
                        .withOptions(new OptionsBuilder().build())
                        //                        .withTools(tools)
                        .withUseTools(true)
                        .build();
        ollamaAPI.registerTools(tools);
        OllamaGenerateStreamObserver handler = new OllamaGenerateStreamObserver(null, null);
        OllamaResult toolsResult = ollamaAPI.generate(request, handler);
        System.out.printf("[Result of executing tool]: %s%n", toolsResult.getResponse());
    }
}
