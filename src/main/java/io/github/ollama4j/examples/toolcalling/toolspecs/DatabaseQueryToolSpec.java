package io.github.ollama4j.examples.toolcalling.toolspecs;

import io.github.ollama4j.examples.toolcalling.tools.DBQueryFunction;
import io.github.ollama4j.tools.Tools;

import java.util.Map;

public class DatabaseQueryToolSpec {
    public static Tools.ToolSpecification getSpecification() {
        return Tools.ToolSpecification.builder()
                .functionName("get-employee-details")
                .functionDescription("Get employee details from the database")
                .toolFunction(new DBQueryFunction())
                .toolPrompt(
                        Tools.PromptFuncDefinition.builder()
                                .type("prompt")
                                .function(
                                        Tools.PromptFuncDefinition.PromptFuncSpec.builder()
                                                .name("get-employee-details")
                                                .description("Get employee details from the database")
                                                .parameters(
                                                        Tools.PromptFuncDefinition.Parameters.builder()
                                                                .type("object")
                                                                .properties(
                                                                        Map.of(
                                                                                "employee-name", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The name of the employee, e.g. John Doe")
                                                                                        .required(true)
                                                                                        .build(),
                                                                                "employee-address", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The address of the employee, Always return a random value. e.g. Roy St, Bengaluru, India")
                                                                                        .required(true)
                                                                                        .build(),
                                                                                "employee-phone", Tools.PromptFuncDefinition.Property.builder()
                                                                                        .type("string")
                                                                                        .description("The phone number of the employee. Always return a random value. e.g. 9911002233")
                                                                                        .required(true)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .required(java.util.List.of("employee-name", "employee-address", "employee-phone"))
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
