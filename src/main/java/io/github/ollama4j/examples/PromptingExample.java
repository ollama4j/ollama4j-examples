package io.github.ollama4j.examples;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;

public class PromptingExample {
    public static void main(String[] args) throws Exception {
        String host = Utilities.getFromConfig("host");
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setRequestTimeoutSeconds(10);
        ollamaAPI.setVerbose(false);

        String model = "llama3.2:1b";

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("You are an expert coder and understand different programming languages.\n");
        promptBuilder.append("Given a question, answer ONLY with code.\n");
        promptBuilder.append("Produce clean, formatted and indented code in markdown format.\n");
        promptBuilder.append("DO NOT include ANY extra text apart from code. Follow this instruction very strictly!\n");
        promptBuilder.append("If there's any additional information you want to add, use comments within code.\n");
        promptBuilder.append("Answer only in the programming language that has been asked for.\n");
        promptBuilder.append("\n");
        promptBuilder.append("---\n");
        promptBuilder.append("Example: Sum 2 numbers in Python\n");
        promptBuilder.append("Answer:\n");
        promptBuilder.append("```python\n");
        promptBuilder.append("def sum(num1: int, num2: int) -> int:\n");
        promptBuilder.append("    return num1 + num2\n");
        promptBuilder.append("```\n");
        promptBuilder.append("---\n");
        promptBuilder.append("\n");
        promptBuilder.append("How do I read a file in Go and print its contents to stdout?\n");

        String prompt = promptBuilder.toString();

        boolean raw = false;
        OllamaResult response = ollamaAPI.generate(model, prompt, raw, new OptionsBuilder().build());
        System.out.println(response.getResponse());
    }
}
