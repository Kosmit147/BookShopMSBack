package com.example;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static final int port = 4200;

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