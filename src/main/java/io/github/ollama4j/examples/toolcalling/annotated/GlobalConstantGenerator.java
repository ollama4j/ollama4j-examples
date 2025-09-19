package io.github.ollama4j.examples.toolcalling.annotated;

import io.github.ollama4j.tools.annotations.ToolProperty;
import io.github.ollama4j.tools.annotations.ToolSpec;
import java.math.BigDecimal;

public class GlobalConstantGenerator {
    public GlobalConstantGenerator() {}

    @ToolSpec(desc = "Computes the most important constant all around the globe!")
    public String generateGlobalConstant(
            @ToolProperty(name = "noOfDigits", desc = "Number of digits that shall be returned")
                    Integer noOfDigits) {
        return BigDecimal.valueOf((long) (Math.random() * 1000000L), noOfDigits).toString();
    }
}
