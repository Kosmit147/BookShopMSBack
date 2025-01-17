package com.example;

import com.example.dto.BookDto;
import com.example.dto.ErrorDto;
import com.example.requests.*;
import com.example.responses.*;
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

        String request;

        while ((request = in.readLine()) != null) {
            String response = handleRequest(request);
            out.println(response);
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

    private String handleRequest(String requestStr) {
        String[] parts = requestStr.split(":", 2);

        String header = parts[0];
        String content = parts.length >= 2 ? parts[1] : "";

        try {
            switch (RequestType.fromRequestHeader(header)) {
                case AddBook -> { return addBook(new AddBookRequest(content)); }
                case AddUser -> { return addUser(new AddUserRequest(content)); }
                case SelectBooks -> { return selectBooks(); }
                default -> { return new ErrorResponse(new ErrorDto("Invalid Request")).create(); }
            }
        } catch (JsonProcessingException e) {
            return new ErrorResponse(new ErrorDto(e.toString())).create();
        }
    }

    private String addBook(AddBookRequest addBookRequest) {
        try {
            dbConnection.addBook(addBookRequest.book);
            return new OkResponse().create();
        } catch (SQLException e) {
            return new ErrorResponse(new ErrorDto(e.toString())).create();
        }
    }

    private String addUser(AddUserRequest addUserRequest) {
        try {
            dbConnection.addUser(addUserRequest.user);
            return new OkResponse().create();
        } catch (SQLException e) {
            return new ErrorResponse(new ErrorDto(e.toString())).create();
        }
    }

    private String selectBooks() {
        try {
            BookDto[] books = dbConnection.selectBooks().toArray(new BookDto[0]);
            return new SelectBooksResponse(books).create();
        } catch (SQLException | JsonProcessingException e) {
            return new ErrorResponse(new ErrorDto(e.toString())).create();
        }
    }
}
