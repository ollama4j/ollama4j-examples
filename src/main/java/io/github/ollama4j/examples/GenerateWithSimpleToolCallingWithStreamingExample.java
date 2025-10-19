package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.examples.tools.toolspecs.EmployeeFinderToolSpec;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.tools.Tools;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.Utilities;
import java.io.IOException;

public class GenerateWithSimpleToolCallingWithStreamingExample {
    public static void main(String[] args) throws Exception {

        Ollama ollama = Utilities.setUp();
        // We're just using our quick-setup utility here to instantiate Ollama. Use the following
        // to set it up with your Ollama configuration.
        // Ollama ollama = new Ollama("http://your-ollama-host:11434/");
        String model = "mistral:7b";
        askModel(ollama, model);
    }

    public static void askModel(Ollama ollama, String modelName)
            throws OllamaException, IOException {

        ollama.pullModel(modelName);

        Tools.Tool toolSpecification = EmployeeFinderToolSpec.getSpecification();

        ollama.registerTool(toolSpecification);

        String prompt = "Give me the details of the employee named 'Rahul Kumar'?";
        OllamaGenerateTokenHandler handler =
                new OllamaGenerateTokenHandler() {
                    @Override
                    public void accept(String message) {
                        System.out.print(message.toUpperCase());
                    }
                };
        OllamaResult toolsResult =
                ollama.generate(
                        OllamaGenerateRequest.builder()
                                .withModel(modelName)
                                .withPrompt(prompt)
                                .withOptions(new OptionsBuilder().build())
                                .build(),
                        new OllamaGenerateStreamObserver(null, handler));
        // for (OllamaToolsResult.ToolResult r : toolsResult.getToolResults()) {
        //     System.out.printf(
        //             "[Result of executing tool '%s']: %s%n",
        //             r.getFunctionName(), r.getResult().toString());
        // }
    }
}
