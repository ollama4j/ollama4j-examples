/*
 * Ollama4j - Java library for interacting with Ollama server.
 * Copyright (c) 2025 Amith Koujalgi and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 *
 */
package io.github.ollama4j.examples;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.github.ollama4j.agent.Agent;
import io.github.ollama4j.exceptions.OllamaException;
import org.slf4j.LoggerFactory;

/**
 * Example usage of the Agent API with some dummy tool functions.
 */
public class AgentExample {
    public static void main(String[] args) throws OllamaException {
        // Disable debug logging and enable only INFO for logback
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);

        // This is using an agent yaml file from classpath, but you can also use a different file
        // path.
        Agent agent = Agent.load("agent.yaml");

        // Run the agent in interactive mode.
        agent.runInteractive();
    }
}
