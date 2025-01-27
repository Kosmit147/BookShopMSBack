package com.example;

import com.example.dto.*;
import com.example.repositories.BookRepository;
import com.example.repositories.OrderRepository;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
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

    public void start(int port) throws IOException, SQLException {
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
            DbConnection.close();
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
                case AddOrder -> { return addOrder(new AddOrderRequest(content)); }
                case AddRole -> { return addRole(new AddRoleRequest(content)); }
                case AddUser -> { return addUser(new AddUserRequest(content)); }
                case AddUserWithRole -> { return addUserWithRole(new AddUserWithRoleRequest(content)); }
                case ChangeOrderStatus -> { return changeOrderStatus(new ChangeOrderStatusRequest(content)); }
                case DeleteBook -> { return deleteBook(new DeleteBookRequest(content)); }
                case DeleteUser -> { return deleteUser(new DeleteUserRequest(content)); }
                case SelectBook -> { return selectBook(new SelectBookRequest(content)); }
                case SelectBooks -> { return selectBooks(); }
                case SelectBooksForOrder -> { return selectBooksForOrder(new SelectBooksForOrderRequest(content)); }
                case SelectOrders -> { return selectOrders(); }
                case SelectOrdersForUser -> { return selectOrdersForUser(new SelectOrdersForUserRequest(content)); }
                case SelectUser -> { return selectUser(new SelectUserRequest(content)); }
                case SelectUserForLogin -> { return selectUserForLogin(new SelectUserForLoginRequest(content)); }
                case SelectUsers -> { return selectUsers(); }
                case SelectRoles -> { return selectRoles(); }
                case UpdateBook -> { return updateBook(new UpdateBookRequest(content)); }
                case UpdateUser -> { return updateUser(new UpdateUserRequest(content)); }
                default -> { return new ErrorResponse(new StringDto("Invalid Request")).create(); }
            }
        } catch (NotFoundException e) {
            return new NotFoundResponse().create();
        } catch (SQLException | JsonProcessingException e) {
            return new ErrorResponse(new StringDto(e.toString())).create();
        }
    }

    private String addBook(AddBookRequest addBookRequest) throws SQLException {
        BookRepository.addBook(addBookRequest.book);
        return new OkResponse().create();
    }

    private String addOrder(AddOrderRequest addOrderRequest) throws SQLException {
        OrderRepository.addOrder(addOrderRequest.order);
        return new OkResponse().create();
    }

    private String addRole(AddRoleRequest addRoleRequest) throws SQLException {
        RoleRepository.addRole(addRoleRequest.role);
        return new OkResponse().create();
    }

    private String addUser(AddUserRequest addUserRequest) throws SQLException {
        UserRepository.addUser(addUserRequest.user);
        return new OkResponse().create();
    }

    private String addUserWithRole(AddUserWithRoleRequest addUserWithRoleRequest) throws SQLException {
        UserRepository.addUserWithRole(addUserWithRoleRequest.user);
        return new OkResponse().create();
    }

    private String changeOrderStatus(ChangeOrderStatusRequest changeOrderStatusRequest) throws SQLException, NotFoundException {
        OrderRepository.changeOrderStatus(changeOrderStatusRequest.changeOrderStatus);
        return new OkResponse().create();
    }

    private String deleteBook(DeleteBookRequest deleteBookRequest) throws SQLException, NotFoundException {
        BookRepository.deleteBook(deleteBookRequest.id.getId());
        return new OkResponse().create();
    }

    private String deleteUser(DeleteUserRequest deleteUserRequest) throws SQLException, NotFoundException {
        UserRepository.deleteUser(deleteUserRequest.id.getId());
        return new OkResponse().create();
    }

    private String selectBook(SelectBookRequest selectBookRequest) throws SQLException, NotFoundException, JsonProcessingException {
        BookDto book = BookRepository.selectBookById(selectBookRequest.id.getId());
        return new SelectBookResponse(book).create();
    }

    private String selectBooks() throws SQLException, JsonProcessingException {
        BookDto[] books = BookRepository.selectBooks().toArray(new BookDto[0]);
        return new SelectBooksResponse(books).create();
    }

    private String selectBooksForOrder(SelectBooksForOrderRequest selectBooksForOrderRequest) throws SQLException, JsonProcessingException, NotFoundException {
        BookOrderDetailsDto[] books = OrderRepository.selectBooksForOrder(selectBooksForOrderRequest.orderId.getId()).toArray(new BookOrderDetailsDto[0]);
        return new SelectBooksForOrderResponse(books).create();
    }

    private String selectOrders() throws SQLException, JsonProcessingException {
        OrderDto[] orders = OrderRepository.selectOrders().toArray(new OrderDto[0]);
        return new SelectOrdersResponse(orders).create();
    }

    private String selectOrdersForUser(SelectOrdersForUserRequest selectOrdersForUserRequest) throws SQLException, JsonProcessingException {
        OrderDto[] orders = OrderRepository.selectOrdersForUser(selectOrdersForUserRequest.userId.getId()).toArray(new OrderDto[0]);
        // TODO: return sth other than selectOrdersResponse
        return new SelectOrdersResponse(orders).create();
    }

    private String selectUser(SelectUserRequest selectUserRequest) throws SQLException, NotFoundException, JsonProcessingException {
        UserDto user = UserRepository.selectUserById(selectUserRequest.id.getId());
        return new SelectUserResponse(user).create();
    }

    private String selectUserForLogin(SelectUserForLoginRequest selectUserRequest) throws SQLException, NotFoundException, JsonProcessingException {
        UserDto user = UserRepository.selectUserForLogin(selectUserRequest.loginData);
        // TODO: return sth other than selectUserResponse
        return new SelectUserResponse(user).create();
    }

    private String selectUsers() throws SQLException, NotFoundException, JsonProcessingException {
        UserDto[] users = UserRepository.selectUsers().toArray(new UserDto[0]);
        return new SelectUsersResponse(users).create();
    }

    private String selectRoles() throws SQLException, NotFoundException, JsonProcessingException {
        RoleDto[] roles = RoleRepository.selectRoles().toArray(new RoleDto[0]);
        return new SelectRolesResponse(roles).create();
    }

    private String updateBook(UpdateBookRequest updateBookRequest) throws SQLException, NotFoundException {
        BookRepository.updateBook(updateBookRequest.book);
        return new OkResponse().create();
    }

    private String updateUser(UpdateUserRequest updateUserRequest) throws SQLException, NotFoundException {
        UserRepository.updateUser(updateUserRequest.user);
        return new OkResponse().create();
    }
}
