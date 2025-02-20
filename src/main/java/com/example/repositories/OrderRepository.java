package com.example.repositories;

import com.example.NotFoundException;
import com.example.dto.*;

import java.sql.*;
import java.util.ArrayList;

public class OrderRepository {
    public static void addOrder(Connection connection, NewOrderDto order) throws SQLException, NotFoundException {
        String addOrder = """
                INSERT INTO orders(first_name, last_name, street, city, zip, date, status_id, user_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addOrder, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, order.firstName);
        stmt.setString(2, order.lastName);
        stmt.setString(3, order.street);
        stmt.setString(4, order.city);
        stmt.setString(5, order.zip);
        stmt.setString(6, order.date);
        int statusId = OrderStatusRepository.selectOrderStatusId(connection, OrderStatus.initialOrderStatus);
        stmt.setInt(7, statusId);
        stmt.setInt(8, order.userId);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows <= 0)
            return;

        int orderId = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        if (generatedKeys.next())
            orderId = generatedKeys.getInt(1);

        for (BookOrderInfoDto bookInfo : order.books)
            addBookToOrder(connection, bookInfo.quantity, bookInfo.id, orderId);
    }

    public static void addBookToOrder(Connection connection, int quantity, int bookId, int orderId) throws SQLException {
        String addBook = """
                INSERT OR REPLACE INTO books_orders(quantity, book_id, order_id) VALUES(?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);

        stmt.setInt(1, quantity);
        stmt.setInt(2, bookId);
        stmt.setInt(3, orderId);

        stmt.executeUpdate();
    }

    public static void updateOrderStatus(Connection connection, UpdateOrderStatusDto updateOrderStatus) throws SQLException, NotFoundException {
        String changeStatus = """
                UPDATE orders
                    SET status_id = ?
                    WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(changeStatus);
        int statusId = OrderStatusRepository.selectOrderStatusId(connection, updateOrderStatus.newStatus);
        stmt.setInt(1, statusId);
        stmt.setInt(2, updateOrderStatus.id);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected < 1)
            throw new NotFoundException();
    }

    public static ArrayList<OrderDto> selectOrders(Connection connection) throws SQLException, NotFoundException {
        String selectOrders = """
                SELECT * FROM orders;
                """;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(selectOrders);

        ArrayList<OrderDto> result = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String street = rs.getString("street");
            String city = rs.getString("city");
            String zip = rs.getString("zip");
            String date = rs.getString("date");
            int statusId = rs.getInt("status_id");
            OrderStatus status = OrderStatusRepository.selectOrderStatusById(connection, statusId);
            int userId = rs.getInt("user_id");

            result.add(new OrderDto(id, firstName, lastName, street, city, zip, date, status, userId));
        }

        return result;
    }

    public static ArrayList<BookOrderDetailsDto> selectBooksForOrder(Connection connection, int orderId) throws SQLException, NotFoundException {
        String selectBooksOrders = """
                SELECT quantity, book_id FROM books_orders WHERE order_id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectBooksOrders);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        ArrayList<BookOrderDetailsDto> result = new ArrayList<>();

        while (rs.next()) {
            int quantity = rs.getInt("quantity");
            int bookId = rs.getInt("book_id");

            BookDto book = BookRepository.selectBookById(connection, bookId);
            result.add(new BookOrderDetailsDto(quantity, book));
        }

        return result;
    }

    public static OrderStatus selectStatusForOrder(Connection connection, int orderId) throws SQLException, NotFoundException {
        String selectStatusId = """
                SELECT status_id FROM orders WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectStatusId);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next())
            throw new NotFoundException();

        int statusId = rs.getInt("status_id");
        return OrderStatusRepository.selectOrderStatusById(connection, statusId);
    }

    public static UserDto selectUserForOrder(Connection connection, int orderId) throws SQLException, NotFoundException {
        String selectUserId = """
                SELECT user_id FROM orders WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectUserId);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next())
            throw new NotFoundException();

        int userId = rs.getInt("user_id");
        return UserRepository.selectUserById(connection, userId);
    }

    public static ArrayList<OrderDto> selectOrdersForUser(Connection connection, int userId) throws SQLException, NotFoundException {
        String selectOrders = """
                SELECT * FROM orders WHERE user_id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectOrders);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        ArrayList<OrderDto> result = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String street = rs.getString("street");
            String city = rs.getString("city");
            String zip = rs.getString("zip");
            String date = rs.getString("date");
            int statusId = rs.getInt("status_id");
            OrderStatus status = OrderStatusRepository.selectOrderStatusById(connection, statusId);

            result.add(new OrderDto(id, firstName, lastName, street, city, zip, date, status, userId));
        }

        return result;
    }
}
