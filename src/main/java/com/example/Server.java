package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents a multi-threaded server that listens for client connections,
 * handles them using a thread pool, and interacts with a database.
 * It initializes the database and manages client connections in a separate thread.
 *
 * <p> It supports basic operations to start and stop the server.</p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class Server {

    /**
     * The maximum number of threads allowed in the thread pool.
     */
    public static final int MAX_THREADS = 16;

    /**
     * Logger for logging messages related to the server's operation.
     */
    private static final Logger logger = LogManager.getLogger(Server.class);

    /**
     * The server socket that listens for incoming client connections.
     */
    private ServerSocket serverSocket;

    /**
     * Thread pool used to handle client connections concurrently.
     */
    private final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

    /**
     * Starts the server and listens for client connections on the specified port.
     * Initializes the database connection and repositories.
     *
     * <p> The server continues running until explicitly stopped. Each new client connection
     * is handled by a {@link ClientHandler} running in its own thread from the thread pool. </p>
     *
     * @param port the port on which the server listens for client connections.
     * @throws IOException if an I/O error occurs while starting the server or accepting connections.
     */
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

    /**
     * Stops the server by shutting down the thread pool and closing the server socket.
     * Logs the server shutdown process.
     */
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
