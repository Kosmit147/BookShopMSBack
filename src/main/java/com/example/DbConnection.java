package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    public static final String url = "jdbc:sqlite:bookshop.db";

    Connection connection;

    DbConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to database.");

        createTables();

        // Statement stmt = connection.createStatement();
        // String insertQuery = "INSERT INTO books (title) VALUES ('1984')";
        // stmt.executeUpdate(insertQuery);

        // String selectQuery = "SELECT * FROM books";
        // ResultSet resultSet = stmt.executeQuery(selectQuery);

        // System.out.println("Query Results:");

        // while (resultSet.next()) {
        //     int id = resultSet.getInt("id");
        //     String title = resultSet.getString("title");
        //     System.out.printf("ID: %d, Title: %s%n", id, title);
        // }
    }

    public void close() throws SQLException {
        connection.close();
    }

    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();

        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    price REAL NOT NULL
                );
                """;

        stmt.execute(createTableQuery);
    }
}
