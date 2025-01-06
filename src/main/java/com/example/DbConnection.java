package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DbConnection {
    public static final String url = "jdbc:sqlite:bookshop.db";

    Connection connection;

    DbConnection() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database.");

            Statement stmt = connection.createStatement();

            String createTableQuery = """
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL
                );
                """;

            stmt.execute(createTableQuery);
            String insertQuery = "INSERT INTO books (title) VALUES ('1984')";
            stmt.executeUpdate(insertQuery);

            String selectQuery = "SELECT * FROM books";
            ResultSet resultSet = stmt.executeQuery(selectQuery);

            System.out.println("Query Results:");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                System.out.printf("ID: %d, Title: %s%n", id, title);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
