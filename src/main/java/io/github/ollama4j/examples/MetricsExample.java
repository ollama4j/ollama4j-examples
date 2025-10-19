package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import io.prometheus.client.exporter.HTTPServer;
import java.util.HashMap;
import java.util.Map;

/**
 * Example demonstrating how to use Ollama4j with Prometheus metrics collection.
 *
 * <p>
 * This example shows how to:
 * <ul>
 * <li>Enable metrics collection on the Ollama</li>
 * <li>Start a Prometheus metrics HTTP server</li>
 * <li>Make API calls that will be automatically instrumented</li>
 * <li>View metrics at http://localhost:8080/metrics</li>
 * </ul>
 *
 * <p>
 * To run this example:
 * <ol>
 * <li>Make sure you have Ollama running locally</li>
 * <li>Pull a model: <code>ollama pull qwen3:0.6b</code></li>
 * <li>Run this example</li>
 * <li>Visit http://localhost:8080/metrics to see the metrics</li>
 * </ol>
 */
public class MetricsExample {

    public static void main(String[] args) {
        try {
            // Create Ollama instance with metrics enabled
            Ollama ollama = Utilities.setUp();
            // We're just using our quick-setup utility here to instantiate Ollama. Use the
            // following
            // to set it up with your Ollama configuration.
            // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
            ollama.setMetricsEnabled(true);

            // Start Prometheus metrics HTTP server
            HTTPServer metricsServer = new HTTPServer(8080);
            System.out.println(
                    "Prometheus metrics server started on http://localhost:8080/metrics");

            // Add shutdown hook to properly close the metrics server
            Runtime.getRuntime()
                    .addShutdownHook(
                            new Thread(
                                    () -> {
                                        System.out.println("Shutting down metrics server...");
                                        metricsServer.close();
                                    }));

            // Test ping endpoint
            System.out.println("Testing ping...");
            boolean isReachable = ollama.ping();
            System.out.println("Ollama server reachable: " + isReachable);

            if (isReachable) {
                // Test generate with format
                System.out.println("Testing generate with format...");
                Map<String, Object> format = new HashMap<>();
                format.put("type", "json");

                try {
                    var result =
                            ollama.generate(
                                    OllamaGenerateRequest.builder()
                                            .withModel("qwen3:0.6b")
                                            .withPrompt(
                                                    "Generate a simple JSON object with name and"
                                                            + " age fields")
                                            .withFormat(format)
                                            .build(),
                                    null);

                    System.out.println("Generated response: " + result.getResponse());
                } catch (Exception e) {
                    System.out.println(
                            "Generate failed (this is expected if qwen3:0.6b model is not"
                                    + " available): "
                                    + e.getMessage());
                }

                // // Test embed endpoint
                // System.out.println("Testing embed...");
                // try {
                // OllamaEmbedRequest embedRequest =
                // new OllamaEmbedRequest("qwen3:0.6b", List.of("Hello world"));
                // OllamaEmbedResult embedResponse = ollama.embed(embedRequest);
                // System.out.println(
                // "Embedding generated with "
                // + embedResponse.getEmbeddings().size()
                // + " embeddings");
                // } catch (Exception e) {
                // System.out.println(
                // "Embed failed (this is expected if qwen3:0.6b model is not available): "
                // + e.getMessage());
                // }

                try {
                    OllamaResult res =
                            ollama.generate(
                                    OllamaGenerateRequest.builder()
                                            .withModel("qwen3:0.6b")
                                            .withPrompt("who are you?")
                                            .withRaw(false)
                                            .withThink(true)
                                            .withOptions(
                                                    new OptionsBuilder()
                                                            .setTemperature(0.6f)
                                                            .build())
                                            .build(),
                                    null);
                    System.out.println("Generated response " + res.getResponse());
                } catch (Exception e) {
                    System.out.println("Generate failed" + e.getMessage());
                }
            }

            System.out.println("\nMetrics are now available at: http://localhost:8080/metrics");
            System.out.println("Press Ctrl+C to stop the metrics server");

            // Keep the server running
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
