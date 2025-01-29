package com.example;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main entry point of the server application.
 * <p>
 * This class starts the server, handles the initialization of the server instance, and
 * sets up a shutdown hook to gracefully shut down the server when the application is terminated.
 * </p>
 *
 * <p>
 * The server listens on port 4200 by default. Any errors during the startup are logged with an error level.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class Main {

    /**
     * Logger for logging server-related events and errors.
     */
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The port number the server listens on.
     */
    public static final int port = 4200;

    /**
     * The main method that runs the server.
     * <p>
     * The method sets up a shutdown hook that logs a message when the server is shutting down.
     * It creates a new instance of {@link Server}, starts the server on the specified port,
     * and ensures the server is stopped when the application exits, even in case of an error.
     * </p>
     *
     * @param args command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down server...");
        }));

        Server server = new Server();

        try {
            server.start(port);
        } catch (IOException e) {
            logger.error("Server error: {}", e.getMessage());
        } finally {
            server.stop();
        }
    }
}