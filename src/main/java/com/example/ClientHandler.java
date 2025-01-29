package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class handles communication with a connected client.
 * It reads client requests, processes them, and sends back responses.
 *
 * <p> Each request is logged with a trace level log, and the response is sent back to the client.
 * It also handles closing the client connection gracefully.</p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class ClientHandler implements Runnable {

    /**
     * Logger for logging client interactions.
     */
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    /**
     * The socket representing the connection to the client.
     */
    private final Socket clientSocket;

    /**
     * Output stream to send data to the client.
     */
    private PrintWriter out;

    /**
     * Input stream to read data from the client.
     */
    private BufferedReader in;

    /**
     * Creates a new {@link ClientHandler} instance for the given client socket.
     *
     * @param socket the socket representing the client's connection.
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    /**
     * The main execution method that handles the client connection.
     * <p> It reads client requests in a loop, processes each request,
     * sends the response back, and logs both requests and responses.
     * This method runs in its own thread, allowing multiple clients to be handled concurrently. </p>
     */
    @Override
    public void run() {
        InetAddress socketAddress = clientSocket.getInetAddress();

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String request;
            int requestNumber = 0;

            // Handle client requests until the connection is closed
            while ((request = in.readLine()) != null) {
                logger.trace("REQUEST {}: {}", requestNumber, request);
                String response = handleRequest(request);
                out.println(response);
                logger.trace("RESPONSE {}: {}", requestNumber, response);
                requestNumber++;
            }
        } catch (IOException e) {
            logger.info("Client {} disconnected: {}", socketAddress, e.getMessage());
        } finally {
            stop();
        }
    }

    /**
     * Processes the client's request.
     *
     * @param requestStr the request string sent by the client.
     * @return the response to be sent back to the client.
     */
    private String handleRequest(String requestStr) {
        return new RequestHandler().processRequest(requestStr);
    }

    /**
     * Closes the input and output streams, and the client socket.
     * This method is called when the client disconnects or when an error occurs.
     */
    private void stop() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            logger.error("Error closing client connection: {}", e.getMessage());
        }
    }
}
