package io.github.ollama4j.examples.tools.toolfunctions;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import io.github.ollama4j.tools.ToolFunction;
import java.time.Duration;
import java.util.Map;

public class AirlineCallsignQueryToolFunction implements ToolFunction {
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
        QueryResult result =
                inventoryScope.query(
                        String.format("SELECT * FROM airline WHERE name = '%s'; ", airlineName));

        JsonObject row = (JsonObject) result.rowsAsObject().get(0).get("airline");
        return new AirlineDetail(
                row.getString("callsign"), row.getString("name"), row.getString("country"));
    }
}
