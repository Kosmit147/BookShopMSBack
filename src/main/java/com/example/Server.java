package com.example;

import com.example.messages.AddBookMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.*;
import java.io.*;
import java.sql.SQLException;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private DbConnection dbConnection;

    public void start(int port) throws IOException, SQLException {
        dbConnection = new DbConnection();
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String messageStr;

        while ((messageStr = in.readLine()) != null) {
            try {
                MessageVariant message = createMessage(messageStr);
                handleMessage(message);
            } catch (SQLException | JsonProcessingException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            dbConnection.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private MessageVariant createMessage(String messageStr) throws JsonProcessingException {
        String[] parts = messageStr.split(":", 2);

        String header = parts[0];
        String content = parts[1];

        if (header.equals("AddBook")) {
            return new MessageVariant.AddBookMessageValue(new AddBookMessage(content));
        } else {
            System.out.printf("Error: Unrecognized message header: %s%n", header);
            return new MessageVariant.InvalidMessageValue();
        }
    }

    private void handleMessage(MessageVariant message) throws SQLException {
        switch (message) {
            case MessageVariant.AddBookMessageValue addBookMessageValue -> addBook(addBookMessageValue.value());
            case MessageVariant.InvalidMessageValue invalidMessageValue -> {}
        }
    }

    private void addBook(AddBookMessage addBookMessage) throws SQLException {
        dbConnection.addBook(addBookMessage.book);
    }
}
