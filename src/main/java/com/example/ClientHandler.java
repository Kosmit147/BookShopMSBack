package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        InetAddress socketAddress = clientSocket.getInetAddress();

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String request;
            int requestNumber = 0;

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

    private String handleRequest(String requestStr) {
        return new RequestHandler().processRequest(requestStr);
    }

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
