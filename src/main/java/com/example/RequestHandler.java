package com.example;

import com.example.dto.*;
import com.example.repositories.BookRepository;
import com.example.repositories.OrderRepository;
import com.example.repositories.UserRepository;
import com.example.requests.*;
import com.example.responses.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Handles incoming client requests and processes them by interacting with the
 * appropriate repositories and data transfer objects (DTOs). It supports a variety of operations
 * including adding, deleting, updating, and retrieving books, users, and orders.
 * <p>
 * This class routes the request to specific methods based on the type of request
 * (as determined by the request header), and returns a corresponding response.
 * </p>
 *
 * <p>
 * Each request type is mapped to a repository operation. If an operation is successful,
 * an appropriate response is generated. If an error or exception occurs, an error response is returned.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class RequestHandler {

    /**
     * Processes an incoming request by routing it to the appropriate handler method.
     * The request string is split into a header and content, and based on the header,
     * the appropriate action is taken.
     * <p>
     * This method handles various operations such as adding, deleting, selecting, and updating
     * books, users, and orders by interacting with the corresponding repositories and request/response objects.
     * </p>
     *
     * @param requestStr the incoming request string in the format "header:content".
     * @return the response as a string, which can be either a success or error message in JSON format.
     */
    public String processRequest(String requestStr) {
        String[] parts = requestStr.split(":", 2);

        String header = parts[0];
        String content = parts.length >= 2 ? parts[1] : "";

        try (Connection conn = DbConnection.getConnection()) {
            switch (RequestType.fromRequestHeader(header)) {
                case AddBook -> { return addBook(conn, new AddBookRequest(content)); }
                case AddOrder -> { return addOrder(conn, new AddOrderRequest(content)); }
                case AddUser -> { return addUser(conn, new AddUserRequest(content)); }
                case AddUserWithRole -> { return addUserWithRole(conn, new AddUserWithRoleRequest(content)); }
                case DeleteBook -> { return deleteBook(conn, new DeleteBookRequest(content)); }
                case DeleteUser -> { return deleteUser(conn, new DeleteUserRequest(content)); }
                case SelectBook -> { return selectBook(conn, new SelectBookRequest(content)); }
                case SelectBooks -> { return selectBooks(conn); }
                case SelectBooksForOrder -> { return selectBooksForOrder(conn, new SelectBooksForOrderRequest(content)); }
                case SelectOrders -> { return selectOrders(conn); }
                case SelectOrdersForUser -> { return selectOrdersForUser(conn, new SelectOrdersForUserRequest(content)); }
                case SelectStatusForOrder -> { return selectStatusForOrder(conn, new SelectStatusForOrderRequest(content)); }
                case SelectUser -> { return selectUser(conn, new SelectUserRequest(content)); }
                case SelectUserForLogin -> { return selectUserForLogin(conn, new SelectUserForLoginRequest(content)); }
                case SelectUserForOrder -> { return selectUserForOrder(conn, new SelectUserForOrderRequest(content)); }
                case SelectUsers -> { return selectUsers(conn); }
                case UpdateBook -> { return updateBook(conn, new UpdateBookRequest(content)); }
                case UpdateOrderStatus -> { return updateOrderStatus(conn, new UpdateOrderStatusRequest(content)); }
                case UpdateUser -> { return updateUser(conn, new UpdateUserRequest(content)); }
                default -> { return new ErrorResponse(new ErrorDto("Invalid Request")).create(); }
            }
        } catch (NotFoundException e) {
            return new NotFoundResponse().create();
        } catch (SQLException | JsonProcessingException e) {
            return new ErrorResponse(new ErrorDto(e.toString())).create();
        }
    }

    private String addBook(Connection conn, AddBookRequest addBookRequest) throws SQLException {
        BookRepository.addBook(conn, addBookRequest.book);
        return new OkResponse().create();
    }

    private String addOrder(Connection conn, AddOrderRequest addOrderRequest) throws SQLException, NotFoundException {
        OrderRepository.addOrder(conn, addOrderRequest.order);
        return new OkResponse().create();
    }

    private String addUser(Connection conn, AddUserRequest addUserRequest) throws SQLException, NotFoundException {
        UserRepository.addUser(conn, addUserRequest.user);
        return new OkResponse().create();
    }

    private String addUserWithRole(Connection conn, AddUserWithRoleRequest addUserWithRoleRequest) throws SQLException, NotFoundException {
        UserRepository.addUserWithRole(conn, addUserWithRoleRequest.user);
        return new OkResponse().create();
    }

    private String deleteBook(Connection conn, DeleteBookRequest deleteBookRequest) throws SQLException, NotFoundException {
        BookRepository.deleteBook(conn, deleteBookRequest.id);
        return new OkResponse().create();
    }

    private String deleteUser(Connection conn, DeleteUserRequest deleteUserRequest) throws SQLException, NotFoundException {
        UserRepository.deleteUser(conn, deleteUserRequest.id);
        return new OkResponse().create();
    }

    private String selectBook(Connection conn, SelectBookRequest selectBookRequest) throws SQLException, NotFoundException, JsonProcessingException {
        BookDto book = BookRepository.selectBookById(conn, selectBookRequest.id);
        return new SelectBookResponse(book).create();
    }

    private String selectBooks(Connection conn) throws SQLException, JsonProcessingException {
        BookDto[] books = BookRepository.selectBooks(conn).toArray(new BookDto[0]);
        return new SelectBooksResponse(books).create();
    }

    private String selectBooksForOrder(Connection conn, SelectBooksForOrderRequest selectBooksForOrderRequest) throws SQLException, JsonProcessingException, NotFoundException {
        BookOrderDetailsDto[] books = OrderRepository.selectBooksForOrder(conn, selectBooksForOrderRequest.orderId).toArray(new BookOrderDetailsDto[0]);
        return new SelectBooksForOrderResponse(books).create();
    }

    private String selectOrders(Connection conn) throws SQLException, JsonProcessingException, NotFoundException {
        OrderDto[] orders = OrderRepository.selectOrders(conn).toArray(new OrderDto[0]);
        return new SelectOrdersResponse(orders).create();
    }

    private String selectOrdersForUser(Connection conn, SelectOrdersForUserRequest selectOrdersForUserRequest) throws SQLException, JsonProcessingException, NotFoundException {
        OrderDto[] orders = OrderRepository.selectOrdersForUser(conn, selectOrdersForUserRequest.userId).toArray(new OrderDto[0]);
        return new SelectOrdersResponse(orders).create();
    }

    private String selectStatusForOrder(Connection conn, SelectStatusForOrderRequest selectStatusForOrderRequest) throws SQLException, JsonProcessingException, NotFoundException {
        OrderStatus status = OrderRepository.selectStatusForOrder(conn, selectStatusForOrderRequest.orderId);
        return new SelectStatusForOrderResponse(status).create();
    }

    private String selectUser(Connection conn, SelectUserRequest selectUserRequest) throws SQLException, NotFoundException, JsonProcessingException {
        UserDto user = UserRepository.selectUserById(conn, selectUserRequest.id);
        return new SelectUserResponse(user).create();
    }

    private String selectUserForLogin(Connection conn, SelectUserForLoginRequest selectUserRequest) throws SQLException, NotFoundException, JsonProcessingException {
        UserDto user = UserRepository.selectUserForLogin(conn, selectUserRequest.loginData);
        return new SelectUserResponse(user).create();
    }

    private String selectUserForOrder(Connection conn, SelectUserForOrderRequest selectUserForOrderRequest) throws SQLException, NotFoundException, JsonProcessingException {
        UserDto user = OrderRepository.selectUserForOrder(conn, selectUserForOrderRequest.orderId);
        return new SelectUserResponse(user).create();
    }

    private String selectUsers(Connection conn) throws SQLException, NotFoundException, JsonProcessingException {
        UserDto[] users = UserRepository.selectUsers(conn).toArray(new UserDto[0]);
        return new SelectUsersResponse(users).create();
    }

    private String updateBook(Connection conn, UpdateBookRequest updateBookRequest) throws SQLException, NotFoundException {
        BookRepository.updateBook(conn, updateBookRequest.book);
        return new OkResponse().create();
    }

    private String updateOrderStatus(Connection conn, UpdateOrderStatusRequest updateOrderStatusRequest) throws SQLException, NotFoundException {
        OrderRepository.updateOrderStatus(conn, updateOrderStatusRequest.updateOrderStatus);
        return new OkResponse().create();
    }

    private String updateUser(Connection conn, UpdateUserRequest updateUserRequest) throws SQLException, NotFoundException {
        UserRepository.updateUser(conn, updateUserRequest.user);
        return new OkResponse().create();
    }
}
