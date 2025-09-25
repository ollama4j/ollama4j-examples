package io.github.ollama4j.examples.tools.toolfunctions;

import io.github.ollama4j.tools.ToolFunction;

import java.util.Map;
import java.util.UUID;

public class EmployeeFinderToolFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        if (arguments.size() != 1) {
            return "Not enough data!";
        }
        String employeeName = arguments.get("employee-name").toString();
        String address = null;
        String phone = null;
        if (employeeName.equalsIgnoreCase("Rahul Kumar")) {
            address = "Pune, Maharashtra, India";
            phone = "9911223344";
        } else {
            address = "Karol Bagh, Delhi, India";
            phone = "9911002233";
        }
        // perform DB operations here
        return String.format(
                "Employee Details {ID: %s, Name: %s, Address: %s, Phone: %s}",
                UUID.randomUUID(), employeeName, address, phone);
    }
}
