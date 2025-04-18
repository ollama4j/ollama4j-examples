package io.github.ollama4j.examples.toolcalling.tools;

import io.github.ollama4j.tools.ToolFunction;

import java.util.Map;
import java.util.UUID;

public class DBQueryFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        if (arguments == null || arguments.isEmpty() || arguments.get("employee-name") == null || arguments.get("employee-address") == null || arguments.get("employee-phone") == null) {
            System.out.println("Tool was called but the model failed to provide all the required arguments.");
            return null;
        }
        // perform DB operations here
        return String.format("Employee Details {ID: %s, Name: %s, Address: %s, Phone: %s}", UUID.randomUUID(), arguments.get("employee-name").toString(), arguments.get("employee-address").toString(), arguments.get("employee-phone").toString());
    }
}