package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static AtomicBoolean running = new AtomicBoolean(true);
    public static final int port = 666;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            running.set(false);
        }));

        try {
            Thread serverThread = new Thread(Main::serverThread);
            serverThread.start();

            while (running.get()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    private static void serverThread()
    {
        Server server = new Server();

        try {
            server.start(port);
        } catch (IOException | SQLException e) {
            System.out.println(e.toString());
        } finally {
            server.stop();
            running.set(false);
        }
    }
}