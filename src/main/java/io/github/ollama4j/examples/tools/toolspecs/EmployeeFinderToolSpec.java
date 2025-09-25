package io.github.ollama4j.examples.tools.toolspecs;

import io.github.ollama4j.tools.Tools;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class EmployeeFinderToolSpec {
    private EmployeeFinderToolSpec() { /* empty constructor */ }

    public static Tools.Tool getSpecification() {
        return Tools.Tool.builder()
                .toolSpec(
                        Tools.ToolSpec.builder()
                                .name("get-employee-details")
                                .description("Get employee details from the company database")
                                .parameters(
                                        Tools.Parameters.of(
                                                Map.of(
                                                        "employee-name",
                                                        Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The name of the employee.")
                                                                .required(true)
                                                                .build(),
                                                        "employee-address",
                                                        Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The address of the employee.")
                                                                .required(true)
                                                                .build(),
                                                        "employee-phone", Tools.Property.builder()
                                                                .type("string")
                                                                .description(
                                                                        "The phone number of the employee.")
                                                                .required(true)
                                                                .build()
                                                )
                                        ))
                                .build())
                .toolFunction(
                        arguments -> {
                            String employeeName = arguments.get("employee-name").toString();
                            String address = arguments.get("employee-address").toString();

                            Random random = new Random();
                            long min = 1_000_000_000L;
                            long max = 9_999_999_999L;
                            String phone = String.valueOf(min + ((long) (random.nextDouble() * (max - min))));

                            return String.format(
                                    "Employee Details {ID: %s, Name: %s, Address: %s, Phone: %s}",
                                    UUID.randomUUID(), employeeName, address, phone);
                        })
                .build();
    }
}