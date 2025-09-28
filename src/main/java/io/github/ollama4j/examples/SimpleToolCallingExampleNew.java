package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateRequestBuilder;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleToolCallingExampleNew {
    public static void main(String[] args) throws Exception {
        OllamaAPI ollamaAPI = Utilities.setUp();
        String model = "mistral:7b";
        askModel(ollamaAPI, model);
    }

    public static void askModel(OllamaAPI ollamaAPI, String modelName) throws OllamaException {
        ollamaAPI.pullModel(modelName);

        String prompt1 = "How is the weather in Bengaluru";
        String prompt2 = "How much does diesel cost in Bengaluru";

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
                                    String fuelType = arguments.get("fuelType").toString();
                                    return "Price of "
                                            + fuelType
                                            + " in "
                                            + location
                                            + " is Rs.103/L.";
                                })
                        .build();
        List<Tools.Tool> tools = new ArrayList<>();
        tools.add(weatherTool);
        tools.add(fuelPriceTool);
        ollamaAPI.registerTools(tools);

        OllamaGenerateRequest request1 =
                OllamaGenerateRequestBuilder.builder()
                        .withModel(modelName)
                        .withPrompt(prompt1)
                        .withOptions(new OptionsBuilder().build())
                        //                        .withTools(tools)
                        .withUseTools(true)
                        .build();
        OllamaGenerateRequest request2 =
                OllamaGenerateRequestBuilder.builder()
                        .withModel(modelName)
                        .withPrompt(prompt2)
                        .withOptions(new OptionsBuilder().build())
                        //                        .withTools(tools)
                        .withUseTools(true)
                        .build();

        OllamaGenerateStreamObserver handler = new OllamaGenerateStreamObserver(null, null);
        OllamaResult toolsResult1 = ollamaAPI.generate(request1, handler);
        OllamaResult toolsResult2 = ollamaAPI.generate(request2, handler);
        System.out.printf("[Result of executing tool]: %s%n", toolsResult1.getResponse());
        System.out.printf("[Result of executing tool]: %s%n", toolsResult2.getResponse());
    }
}
