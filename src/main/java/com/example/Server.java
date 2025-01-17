package com.example;

import com.example.dto.BookDto;
import com.example.dto.ServerErrorDto;
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

        String requestStr;

        while ((requestStr = in.readLine()) != null) {
            try {
                // TODO: simplify request parsing, maybe get rid of the variant?
                RequestVariant request = parseRequest(requestStr);
                String response = handleRequest(request);
                out.println(response);
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

    private RequestVariant parseRequest(String requestStr) throws JsonProcessingException {
        String[] parts = requestStr.split(":", 2);

        String header = parts[0];
        String content = parts.length >= 2 ? parts[1] : "";

        switch (RequestType.fromRequestHeader(header)) {
            case AddBook -> { return new RequestVariant.AddBookRequestValue(new AddBookRequest(content)); }
            case AddUser -> { return new RequestVariant.AddUserRequestValue(new AddUserRequest(content)); }
            case SelectBooks -> { return new RequestVariant.SelectBooksRequestValue(new SelectBooksRequest()); }
            default -> { return new RequestVariant.InvalidRequestValue(); }
        }
    }

    private String handleRequest(RequestVariant request) throws SQLException {
        switch (request) {
            case RequestVariant.AddBookRequestValue value -> { return addBook(value.value()); }
            case RequestVariant.AddUserRequestValue value -> { return addUser(value.value()); }
            case RequestVariant.SelectBooksRequestValue value -> { return selectBooks(); }
            case RequestVariant.InvalidRequestValue value -> {
                ServerErrorDto dto = new ServerErrorDto("Invalid Request");
                return new ServerErrorResponse(dto).create();
            }
        }
    }

    private String addBook(AddBookRequest addBookRequest) throws SQLException {
        try {
            dbConnection.addBook(addBookRequest.book);
            return new OkResponse().create();
        } catch (SQLException e) {
            return new ServerErrorResponse(new ServerErrorDto(e.toString())).create();
        }
    }

    private String addUser(AddUserRequest addUserRequest) throws SQLException {
        try {
            dbConnection.addUser(addUserRequest.user);
            return new OkResponse().create();
        } catch (SQLException e) {
            return new ServerErrorResponse(new ServerErrorDto(e.toString())).create();
        }
    }

    private String selectBooks() throws SQLException {
        try {
            BookDto[] books = dbConnection.selectBooks().toArray(new BookDto[0]);
            return new SelectBooksResponse(books).create();
        } catch (SQLException | JsonProcessingException e) {
            return new ServerErrorResponse(new ServerErrorDto(e.toString())).create();
        }
    }
}
