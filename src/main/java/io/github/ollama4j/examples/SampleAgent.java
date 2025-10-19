/*
 * Ollama4j - Java library for interacting with Ollama server.
 * Copyright (c) 2025 Amith Koujalgi and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 *
 */
package io.github.ollama4j.examples;

import io.github.ollama4j.agent.Agent;
import io.github.ollama4j.exceptions.OllamaException;

/** Example usage of the Agent API with some dummy tool functions. */
public class SampleAgent {
    public static void main(String[] args) throws OllamaException {
        Agent agent = Agent.fromYaml("agent.yaml");
        agent.runInteractive();
    }
}
