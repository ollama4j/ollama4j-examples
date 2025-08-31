package io.github.ollama4j.examples.toolcalling.tools;

import io.github.ollama4j.tools.ToolFunction;

import java.util.Map;
import java.util.UUID;

public class DBQueryFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        System.out.println("Invoking employee finder tool with arguments: " + arguments);
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