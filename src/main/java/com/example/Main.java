package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static AtomicBoolean running = new AtomicBoolean(true);
    public static final int port = 4200;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down server...");
            running.set(false);
        }));

        try {
            Thread serverThread = new Thread(Main::serverThread);
            serverThread.start();

            while (running.get()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
    }

    private static void serverThread()
    {
        Server server = new Server();

        try {
            server.start(port);
        } catch (IOException | SQLException e) {
            logger.error(e.toString());
        } finally {
            server.stop();
            running.set(false);
        }
    }
}