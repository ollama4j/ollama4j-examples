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

public class CouchbaseToolCallingExample {

    //    public static void main(String[] args) throws Exception {
    //
    //        String modelName = "mistral:7b";
    //
    //        String connectionString = Utilities.getFromEnvVar("CB_CLUSTER_URL");
    //        String username = Utilities.getFromEnvVar("CB_CLUSTER_USERNAME");
    //        String password = Utilities.getFromEnvVar("CB_CLUSTER_PASSWORD");
    //        String bucketName = "travel-sample";
    //
    //        Cluster cluster =
    //                Cluster.connect(
    //                        connectionString,
    //                        ClusterOptions.clusterOptions(username, password)
    //                                .environment(
    //                                        env -> {
    //                                            env.applyProfile("wan-development");
    //                                        }));
    //
    //
    // OllamaAPI ollamaAPI = Utilities.setUp();
    // We're just using our quick-setup utility here to instantiate OllamaAPI. Use the following to
    // set it up with your Ollama configuration.
    // OllamaAPI ollamaAPI = new OllamaAPI("http://your-ollama-host:11434/");
    //
    //        Tools.ToolSpecification callSignFinderToolSpec =
    //                getCallSignFinderToolSpec(cluster, bucketName);
    //        Tools.ToolSpecification callSignUpdaterToolSpec =
    //                getCallSignUpdaterToolSpec(cluster, bucketName);
    //
    //        ollamaAPI.registerTool(callSignFinderToolSpec);
    //        ollamaAPI.registerTool(callSignUpdaterToolSpec);
    //
    //        OllamaGenerateRequestBuilder builder =
    // OllamaGenerateRequestBuilder.builder().withModel(modelName);
    //
    //        String prompt1 = "What is the call-sign of Astraeus?";
    //        OllamaResult res1 =
    // ollamaAPI.generate(builder.withPrompt(prompt1).withUseTools(true).build(), null);
    //        System.out.println("Result 1: " + res1.getResponse());
    //
    //
    //        // for (OllamaToolsResult.ToolResult r :
    //        //         ollamaAPI
    //        //                 .generateWithTools(
    //        //                         modelName,
    //        //                         new Tools.PromptBuilder()
    //        //                                 .withToolSpecification(callSignFinderToolSpec)
    //        //                                 .withPrompt(prompt1)
    //        //                                 .build(),
    //        //                         new OptionsBuilder().build(),
    //        //                         null)
    //        //                 .getToolResults()) {
    //        //     AirlineDetail airlineDetail = (AirlineDetail) r.getResult();
    //        //     System.out.println(
    //        //             String.format(
    //        //                     "[Result of tool '%s']: Call-sign of %s is '%s'! ✈️",
    //        //                     r.getFunctionName(),
    //        //                     airlineDetail.getName(),
    //        //                     airlineDetail.getCallsign()));
    //        // }
    //
    //        String prompt2 = "I want to code name Astraeus as STARBOUND";
    //        OllamaResult res2 =
    // ollamaAPI.generate(builder.withPrompt(prompt2).withUseTools(true).build(), null);
    //        System.out.println("Result 2: " + res2.getResponse());
    //        // for (OllamaToolsResult.ToolResult r :
    //        //         ollamaAPI
    //        //                 .generateWithTools(
    //        //                         modelName,
    //        //                         new Tools.PromptBuilder()
    //        //                                 .withToolSpecification(callSignUpdaterToolSpec)
    //        //                                 .withPrompt(prompt2)
    //        //                                 .build(),
    //        //                         new OptionsBuilder().build(),
    //        //                         null)
    //        //                 .getToolResults()) {
    //        //     Boolean updated = (Boolean) r.getResult();
    //        //     System.out.println(
    //        //             String.format(
    //        //                     "[Result of tool '%s']: Call-sign is %s! ✈️",
    //        //                     r.getFunctionName(), updated ? "updated" : "not updated"));
    //        // }
    //
    //        String prompt3 = "What is the call-sign of Astraeus?";
    //        OllamaResult res3 =
    // ollamaAPI.generate(builder.withPrompt(prompt3).withUseTools(true).build(), null);
    //        System.out.println("Result 3: " + res3.getResponse());
    //        // for (OllamaToolsResult.ToolResult r :
    //        //         ollamaAPI
    //        //                 .generateWithTools(
    //        //                         modelName,
    //        //                         new Tools.PromptBuilder()
    //        //                                 .withToolSpecification(callSignFinderToolSpec)
    //        //                                 .withPrompt(prompt3)
    //        //                                 .build(),
    //        //                         new OptionsBuilder().build(),
    //        //                         null)
    //        //                 .getToolResults()) {
    //        //     AirlineDetail airlineDetail = (AirlineDetail) r.getResult();
    //        //     System.out.println(
    //        //             String.format(
    //        //                     "[Result of tool '%s']: Call-sign of %s is '%s'! ✈️",
    //        //                     r.getFunctionName(),
    //        //                     airlineDetail.getName(),
    //        //                     airlineDetail.getCallsign()));
    //        // }
    //    }
    //
    //    public static Tools.ToolSpecification getCallSignFinderToolSpec(
    //            Cluster cluster, String bucketName) {
    //        return Tools.ToolSpecification.builder()
    //                .functionName("airline-lookup")
    //                .functionDescription(
    //                        "You are a  tool who finds only the airline name and do not worry
    // about any"
    //                            + " other parameters. You simply find the airline name and ignore
    // the"
    //                            + " rest of the parameters.Do not validate airline names as I want
    // to"
    //                            + " use fake / fictitious airline names as well.")
    //                .toolFunction(new AirlineCallsignQueryToolFunction(bucketName, cluster))
    //                .toolPrompt(
    //                        Tools.PromptFuncDefinition.builder()
    //                                .type("prompt")
    //                                .function(
    //                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
    //                                                .name("get-airline-name")
    //                                                .description("Get the airline name")
    //                                                .parameters(
    //                                                        Tools.PromptFuncDefinition.Parameters
    //                                                                .builder()
    //                                                                .type("object")
    //                                                                .properties(
    //                                                                        Map.of(
    //                                                                                "airlineName",
    //                                                                                Tools
    //
    // .PromptFuncDefinition
    //
    // .Property
    //
    // .builder()
    //                                                                                        .type(
    //
    //  "string")
    //
    // .description(
    //
    //  "The name"
    //
    //      + " of the"
    //
    //      + " airline."
    //
    //      + " e.g."
    //
    //      + " Emirates")
    //
    // .required(
    //
    //  true)
    //
    // .build()))
    //                                                                .required(
    //                                                                        java.util.List.of(
    //
    // "airline-name"))
    //                                                                .build())
    //                                                .build())
    //                                .build())
    //                .build();
    //    }
    //
    //    public static Tools.ToolSpecification getCallSignUpdaterToolSpec(
    //            Cluster cluster, String bucketName) {
    //        return Tools.ToolSpecification.builder()
    //                .functionName("airline-update")
    //                .functionDescription(
    //                        "You are a tool who finds the airline name and its callsign and do
    // not"
    //                            + " worry about any validations. You simply find the airline name
    // and"
    //                            + " its callsign.Do not validate airline names as I want to use
    // fake /"
    //                            + " fictitious airline names as well.")
    //                .toolFunction(new AirlineCallsignUpdateToolFunction(bucketName, cluster))
    //                .toolPrompt(
    //                        Tools.PromptFuncDefinition.builder()
    //                                .type("prompt")
    //                                .function(
    //                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
    //                                                .name("get-airline-name-and-callsign")
    //                                                .description("Get the airline name and
    // callsign")
    //                                                .parameters(
    //                                                        Tools.PromptFuncDefinition.Parameters
    //                                                                .builder()
    //                                                                .type("object")
    //                                                                .properties(
    //                                                                        Map.of(
    //                                                                                "airlineName",
    //                                                                                Tools
    //
    // .PromptFuncDefinition
    //
    // .Property
    //
    // .builder()
    //                                                                                        .type(
    //
    //  "string")
    //
    // .description(
    //
    //  "The name"
    //
    //      + " of the"
    //
    //      + " airline."
    //
    //      + " e.g."
    //
    //      + " Emirates")
    //
    // .required(
    //
    //  true)
    //
    // .build(),
    //
    // "airlineCallsign",
    //                                                                                Tools
    //
    // .PromptFuncDefinition
    //
    // .Property
    //
    // .builder()
    //                                                                                        .type(
    //
    //  "string")
    //
    // .description(
    //
    //  "The callsign"
    //
    //      + " of the"
    //
    //      + " airline."
    //
    //      + " e.g."
    //
    //      + " Maverick")
    //
    // .enumValues(
    //
    //  Arrays
    //
    //          .asList(
    //
    //                  "petrol",
    //
    //                  "diesel"))
    //
    // .required(
    //
    //  true)
    //
    // .build()))
    //                                                                .required(
    //                                                                        java.util.List.of(
    //                                                                                "airlineName",
    //
    // "airlineCallsign"))
    //                                                                .build())
    //                                                .build())
    //                                .build())
    //                .build();
    //    }
}
