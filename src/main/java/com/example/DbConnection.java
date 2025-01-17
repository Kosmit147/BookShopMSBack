package com.example;

import com.example.dto.BookDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DbConnection {
    public static final String url = "jdbc:sqlite:bookshop.db";

    Connection connection;

    DbConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to database.");
        createTables();
    }

    public void addBook(BookDto book) throws SQLException {
        String addBook = """
                INSERT INTO books(title, author, price) VALUES(?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);

        stmt.setString(1, book.title);
        stmt.setString(2, book.author);
        stmt.setBigDecimal(3, book.price);

        stmt.executeUpdate();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();

        String createBooks = """
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    price REAL NOT NULL
                );
                """;

        stmt.execute(createBooks);

        String createRoles = """
                CREATE TABLE IF NOT EXISTS roles (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL
                );
                """;

        stmt.execute(createRoles);

        String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role_id INTEGER NOT NULL,
                
                    FOREIGN KEY (role_id) REFERENCES roles(id)
                );
                """;

        stmt.execute(createUsers);

        String createOrders = """
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """;

        stmt.execute(createOrders);

        String createBooksOrders = """
                CREATE TABLE IF NOT EXISTS books_orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER NOT NULL,
                    order_id INTEGER NOT NULL,
                
                    FOREIGN KEY (book_id) REFERENCES books(id),
                    FOREIGN KEY (order_id) REFERENCES orders(id)
                );
                """;

        stmt.execute(createBooksOrders);
    }
}
