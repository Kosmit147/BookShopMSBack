package com.example.repositories;

import com.example.DbConnection;
import com.example.dto.BookOrderInfo;
import com.example.dto.NewOrderDto;

import java.sql.*;

public class OrderRepository {
    public static void addOrder(NewOrderDto order) throws SQLException {
        Connection connection = DbConnection.getConnection();

        String addOrder = """
                INSERT INTO orders(first_name, last_name, street, city, zip, date, user_id) VALUES(?, ?, ?, ?, ?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addOrder, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, order.firstName);
        stmt.setString(2, order.lastName);
        stmt.setString(3, order.street);
        stmt.setString(4, order.city);
        stmt.setString(5, order.zip);
        stmt.setString(6, order.date);
        stmt.setInt(7, order.userId);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows <= 0)
            return;

        int orderId = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        if (generatedKeys.next())
            orderId = generatedKeys.getInt("id");

        for (BookOrderInfo bookInfo : order.books)
            addBookToOrder(bookInfo.quantity, bookInfo.id, orderId);
    }

    // TODO: should this function be part of OrderRepository?
    private static void addBookToOrder(int quantity, int bookId, int orderId) throws SQLException {
        Connection connection = DbConnection.getConnection();

        String addBook = """
                INSERT INTO books_orders(quantity, book_id, order_id) VALUES(?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);

        stmt.setInt(1, quantity);
        stmt.setInt(2, bookId);
        stmt.setInt(3, orderId);

        stmt.executeUpdate();
    }

}
