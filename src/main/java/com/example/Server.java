package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private ServerSocket serverSocket;
    // TODO: flexible number of threads
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Server started on port {}", port);

        try (Connection connection = DbConnection.getConnection()) {
            DbConnection.createTables(connection);
            DbConnection.initRepositories(connection);
        } catch (SQLException e) {
            logger.error("Error setting up the database: {}", e.getMessage());
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("New client connected: {}", clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.execute(clientHandler);
            } catch (IOException e) {
                logger.error("Error accepting client connection: {}", e.getMessage());
            }
        }
    }

    public void stop() {
        try {
            threadPool.shutdown();
            if (serverSocket != null) serverSocket.close();
            logger.info("Server stopped.");
        } catch (IOException e) {
            logger.error("Error closing server: {}", e.getMessage());
        }
    }
}
