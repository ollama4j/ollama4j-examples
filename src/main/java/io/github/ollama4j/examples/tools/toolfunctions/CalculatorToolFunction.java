package io.github.ollama4j.examples.tools.toolfunctions;

import io.github.ollama4j.tools.ToolFunction;
import java.util.Map;

/**
 * ToolFunction implementation for basic arithmetic calculations.
 */
public class CalculatorToolFunction implements ToolFunction {
    @Override
    public Object apply(Map<String, Object> arguments) {
        String operation = (String) arguments.get("operation");
        double a = ((Number) arguments.get("a")).doubleValue();
        double b = ((Number) arguments.get("b")).doubleValue();
        double result;
        switch (operation.toLowerCase()) {
            case "add":
                result = a + b;
                break;
            case "subtract":
                result = a - b;
                break;
            case "multiply":
                result = a * b;
                break;
            case "divide":
                if (b == 0) {
                    return "Cannot divide by zero.";
                }
                result = a / b;
                break;
            default:
                return "Unknown operation: " + operation;
        }
        return "Result: " + result;
    }
}
