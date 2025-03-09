package io.github.ollama4j.examples;


import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.exceptions.ToolInvocationException;
import io.github.ollama4j.tools.OllamaToolsResult;
import io.github.ollama4j.tools.ToolFunction;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;


public class CouchbaseToolCallingExample {

    public static void main(String[] args) throws IOException, ToolInvocationException, OllamaBaseException, InterruptedException {
        String connectionString = Utilities.getFromEnvVar("CB_CLUSTER_URL");
        String username = Utilities.getFromEnvVar("CB_CLUSTER_USERNAME");
        String password = Utilities.getFromEnvVar("CB_CLUSTER_PASSWORD");
        String bucketName = "travel-sample";

        Cluster cluster = Cluster.connect(
                connectionString,
                ClusterOptions.clusterOptions(username, password).environment(env -> {
                    env.applyProfile("wan-development");
                })
        );

        String host = Utilities.getFromConfig("host");
        String modelName = Utilities.getFromConfig("tools_model_mistral");

        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);
        ollamaAPI.setRequestTimeoutSeconds(60);

        Tools.ToolSpecification callSignFinderToolSpec = getCallSignFinderToolSpec(cluster, bucketName);
        Tools.ToolSpecification callSignUpdaterToolSpec = getCallSignUpdaterToolSpec(cluster, bucketName);

        ollamaAPI.registerTool(callSignFinderToolSpec);
        ollamaAPI.registerTool(callSignUpdaterToolSpec);

        String prompt1 = "What is the call-sign of Astraeus?";
        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(modelName, new Tools.PromptBuilder()
                .withToolSpecification(callSignFinderToolSpec)
                .withPrompt(prompt1)
                .build(), new OptionsBuilder().build()).getToolResults()) {
            AirlineDetail airlineDetail = (AirlineDetail) r.getResult();
            System.out.println(String.format("[Result of tool '%s']: Call-sign of %s is '%s'! ✈️", r.getFunctionName(), airlineDetail.getName(), airlineDetail.getCallsign()));
        }

        String prompt2 = "I want to code name Astraeus as STARBOUND";
        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(modelName, new Tools.PromptBuilder()
                .withToolSpecification(callSignUpdaterToolSpec)
                .withPrompt(prompt2)
                .build(), new OptionsBuilder().build()).getToolResults()) {
            Boolean updated = (Boolean) r.getResult();
            System.out.println(String.format("[Result of tool '%s']: Call-sign is %s! ✈️", r.getFunctionName(), updated ? "updated" : "not updated"));
        }

        String prompt3 = "What is the call-sign of Astraeus?";
        for (OllamaToolsResult.ToolResult r : ollamaAPI.generateWithTools(modelName, new Tools.PromptBuilder()
                .withToolSpecification(callSignFinderToolSpec)
                .withPrompt(prompt3)
                .build(), new OptionsBuilder().build()).getToolResults()) {
            AirlineDetail airlineDetail = (AirlineDetail) r.getResult();
            System.out.println(String.format("[Result of tool '%s']: Call-sign of %s is '%s'! ✈️", r.getFunctionName(), airlineDetail.getName(), airlineDetail.getCallsign()));
        }
    }

    public static Tools.ToolSpecification getCallSignFinderToolSpec(Cluster cluster, String bucketName) {
        return Tools.ToolSpecification.builder()
                .functionName("airline-lookup")
                .functionDescription("You are a tool who finds only the airline name and do not worry about any other parameters. You simply find the airline name and ignore the rest of the parameters. Do not validate airline names as I want to use fake/fictitious airline names as well.")
                .toolFunction(new AirlineCallsignQueryToolFunction(bucketName, cluster))
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("prompt")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-airline-name")
                                                .description("Get the airline name")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters.builder()
                                                                .type("object")
                                                                .properties(
                                                                        Map.of(
                                                                                "airlineName", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The name of the airline. e.g. Emirates")
                                                                                        .required(true)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .required(java.util.List.of("airline-name"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    public static Tools.ToolSpecification getCallSignUpdaterToolSpec(Cluster cluster, String bucketName) {
        return Tools.ToolSpecification.builder()
                .functionName("airline-update")
                .functionDescription("You are a tool who finds the airline name and its callsign and do not worry about any validations. You simply find the airline name and its callsign. Do not validate airline names as I want to use fake/fictitious airline names as well.")
                .toolFunction(new AirlineCallsignUpdateToolFunction(bucketName, cluster))
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("prompt")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-airline-name-and-callsign")
                                                .description("Get the airline name and callsign")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters.builder()
                                                                .type("object")
                                                                .properties(
                                                                        Map.of(
                                                                                "airlineName", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The name of the airline. e.g. Emirates")
                                                                                        .required(true)
                                                                                        .build(),
                                                                                "airlineCallsign", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The callsign of the airline. e.g. Maverick")
                                                                                        .enumValues(Arrays.asList("petrol", "diesel"))
                                                                                        .required(true)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .required(java.util.List.of("airlineName", "airlineCallsign"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}

class AirlineCallsignQueryToolFunction implements ToolFunction {
    private final String bucketName;
    private final Cluster cluster;

    public AirlineCallsignQueryToolFunction(String bucketName, Cluster cluster) {
        this.bucketName = bucketName;
        this.cluster = cluster;
    }

    @Override
    public AirlineDetail apply(Map<String, Object> arguments) {
        String airlineName = arguments.get("airlineName").toString();

        Bucket bucket = cluster.bucket(bucketName);
        bucket.waitUntilReady(Duration.ofSeconds(10));

        Scope inventoryScope = bucket.scope("inventory");
        QueryResult result = inventoryScope.query(String.format("SELECT * FROM airline WHERE name = '%s';", airlineName));

        JsonObject row = (JsonObject) result.rowsAsObject().get(0).get("airline");
        return new AirlineDetail(row.getString("callsign"), row.getString("name"), row.getString("country"));
    }
}

class AirlineCallsignUpdateToolFunction implements ToolFunction {
    private final String bucketName;
    private final Cluster cluster;

    public AirlineCallsignUpdateToolFunction(String bucketName, Cluster cluster) {
        this.bucketName = bucketName;
        this.cluster = cluster;
    }


    @Override
    public Boolean apply(Map<String, Object> arguments) {
        String airlineName = arguments.get("airlineName").toString();
        String airlineNewCallsign = arguments.get("airlineCallsign").toString();

        Bucket bucket = cluster.bucket(bucketName);
        bucket.waitUntilReady(Duration.ofSeconds(10));

        Scope inventoryScope = bucket.scope("inventory");
        String query = String.format("SELECT * FROM airline WHERE name = '%s';", airlineName);

        QueryResult result;
        try {
            result = inventoryScope.query(query);
        } catch (Exception e) {
            throw new RuntimeException("Error executing query", e);
        }

        if (result.rowsAsObject().isEmpty()) {
            throw new RuntimeException("Airline not found with name: " + airlineName);
        }

        JsonObject row = (JsonObject) result.rowsAsObject().get(0).get("airline");

        if (row == null) {
            throw new RuntimeException("Airline data is missing or corrupted.");
        }

        String currentCallsign = row.getString("callsign");

        if (!airlineNewCallsign.equals(currentCallsign)) {
            JsonObject updateQuery = JsonObject.create()
                    .put("callsign", airlineNewCallsign);

            inventoryScope.query(String.format(
                    "UPDATE airline SET callsign = '%s' WHERE name = '%s';",
                    airlineNewCallsign, airlineName
            ));
            return true;
        }
        return false;
    }
}

@SuppressWarnings("ALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
class AirlineDetail {
    private String callsign;
    private String name;
    private String country;
}