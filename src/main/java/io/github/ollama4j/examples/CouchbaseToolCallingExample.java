package io.github.ollama4j.examples;

// Sample Couchbase Airlines data to be inserted into travel-sample bucket
// You can use Couchbase's Query Workbench or any Couchbase SDK to insert these documents
// into the `travel-sample` bucket under the `inventory` scope and `airline
// ` collection.
//
// -- Astraeus Airlines
// INSERT INTO `travel-sample`.`inventory`.`airline` (KEY, VALUE)
// VALUES ("airline_10001", {
//    "id": 10001,
//            "type": "airline",
//            "name": "Astraeus",
//            "iata": "ASQ",
//            "icao": "AST",
//            "callsign": "STARBOUND",
//            "country": "United Kingdom"
// });
//
//        -- Galactic Airways
// INSERT INTO `travel-sample`.`inventory`.`airline` (KEY, VALUE)
// VALUES ("airline_10002", {
//    "id": 10002,
//            "type": "airline",
//            "name": "Galactic Airways",
//            "iata": "GLX",
//            "icao": "GLA",
//            "callsign": "COSMOS",
//            "country": "USA"
// });
//
//        -- Oceanic Airlines (fictional one from TV shows 😉)
// INSERT INTO `travel-sample`.`inventory`.`airline` (KEY, VALUE)
// VALUES ("airline_10003", {
//    "id": 10003,
//            "type": "airline",
//            "name": "Oceanic Airlines",
//            "iata": "OCN",
//            "icao": "OCE",
//            "callsign": "WAVES",
//            "country": "Australia"
// });
//
//        -- Skyward Express
// INSERT INTO `travel-sample`.`inventory`.`airline` (KEY, VALUE)
// VALUES ("airline_10004", {
//    "id": 10004,
//            "type": "airline",
//            "name": "Skyward Express",
//            "iata": "SKY",
//            "icao": "SKE",
//            "callsign": "FLYHIGH",
//            "country": "India"
// });

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import io.github.ollama4j.Ollama;
import io.github.ollama4j.examples.tools.toolfunctions.AirlineCallsignQueryToolFunction;
import io.github.ollama4j.examples.tools.toolfunctions.AirlineCallsignUpdateToolFunction;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.Utilities;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class CouchbaseToolCallingExample {

    public static void main(String[] args) throws Exception {
        // Disable debug logging and enable only INFO for logback
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);

        String modelName = "mistral:7b";

        String connectionString = Utilities.getFromEnvVar("CB_CLUSTER_URL");
        String username = Utilities.getFromEnvVar("CB_CLUSTER_USERNAME");
        String password = Utilities.getFromEnvVar("CB_CLUSTER_PASSWORD");
        String bucketName = "travel-sample";

        Cluster cluster =
                Cluster.connect(
                        connectionString,
                        ClusterOptions.clusterOptions(username, password)
                                .environment(env -> env.applyProfile("wan-development")));

        Ollama ollama = Utilities.setUp();
        // We 're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to
        // set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");

        Tools.Tool callSignFinderToolSpec =
                CouchbaseToolCallingExample.getCallSignFinderTool(cluster, bucketName);
        Tools.Tool callSignUpdaterToolSpec =
                CouchbaseToolCallingExample.getCallSignUpdaterTool(cluster, bucketName);

        ollama.registerTool(callSignFinderToolSpec);
        ollama.registerTool(callSignUpdaterToolSpec);

        OllamaGenerateRequest builder = OllamaGenerateRequest.builder().withModel(modelName);

        String prompt1 = "What is the call-sign of Astraeus?";
        OllamaResult res1 =
                ollama.generate(builder.withPrompt(prompt1).withUseTools(true).build(), null);
        System.out.println("Result 1: " + res1.getResponse());

        String prompt2 = "I want to code name Astraeus as STARBOUND";
        OllamaResult res2 =
                ollama.generate(builder.withPrompt(prompt2).withUseTools(true).build(), null);
        System.out.println("Result 2: " + res2.getResponse());
    }

    public static Tools.Tool getCallSignFinderTool(Cluster cluster, String bucketName) {
        return Tools.Tool.builder()
                .toolSpec(
                        Tools.ToolSpec.builder()
                                .name("airline-finder")
                                .description("Finds details of an airline by airline name.")
                                .parameters(
                                        Tools.Parameters.of(
                                                Map.of(
                                                        "airlineName",
                                                        Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The name of the airline."
                                                                                + " e.g. Emirates")
                                                                .required(true)
                                                                .build())))
                                .build())
                .toolFunction(
                        arguments ->
                                new AirlineCallsignQueryToolFunction(bucketName, cluster)
                                        .apply(arguments))
                .build();
    }

    public static Tools.Tool getCallSignUpdaterTool(Cluster cluster, String bucketName) {
        return Tools.Tool.builder()
                .toolSpec(
                        Tools.ToolSpec.builder()
                                .name("airline-updater")
                                .description("Updates the specified airline's callsign.")
                                .parameters(
                                        Tools.Parameters.of(
                                                Map.of(
                                                        "airlineName",
                                                        Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The name of the airline."
                                                                                + " e.g. Emirates")
                                                                .required(true)
                                                                .build(),
                                                        "airlineCallsign",
                                                        Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The callsign of the"
                                                                                + " airline. e.g."
                                                                                + " Maverick")
                                                                .required(true)
                                                                .build())))
                                .build())
                .toolFunction(
                        arguments ->
                                new AirlineCallsignUpdateToolFunction(bucketName, cluster)
                                        .apply(arguments))
                .build();
    }
}
