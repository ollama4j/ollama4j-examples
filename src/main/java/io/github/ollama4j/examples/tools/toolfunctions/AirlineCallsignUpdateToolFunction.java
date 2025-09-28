package io.github.ollama4j.examples.tools.toolfunctions;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import io.github.ollama4j.tools.ToolFunction;
import java.time.Duration;
import java.util.Map;

public class AirlineCallsignUpdateToolFunction implements ToolFunction {
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
            JsonObject updateQuery = JsonObject.create().put("callsign", airlineNewCallsign);

            inventoryScope.query(
                    String.format(
                            "UPDATE airline SET callsign = '%s' WHERE name = '%s';",
                            airlineNewCallsign, airlineName));
            return true;
        }
        return false;
    }
}
