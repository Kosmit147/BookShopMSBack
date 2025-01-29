package com.example;

import com.example.repositories.OrderStatusRepository;
import com.example.repositories.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private static final Logger logger = LogManager.getLogger(DbConnection.class);
    public static final String url = "jdbc:sqlite:bookshop.db";
    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url);
            logger.info("Connected to database.");
            createTables();
            initRepositories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    private static void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");

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
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                );
                """;

        stmt.execute(createUsers);

        String createOrderStatuses = """
                CREATE TABLE IF NOT EXISTS order_statuses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL
                );
                """;

        stmt.execute(createOrderStatuses);

        String createOrders = """
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    street TEXT NOT NULL,
                    city TEXT NOT NULL,
                    zip TEXT NOT NULL,
                    date TEXT NOT NULL,
                    status_id INTEGER NOT NULL,
                    user_id INTEGER NOT NULL,
                
                    FOREIGN KEY (status_id) REFERENCES order_statuses(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                
                    FOREIGN KEY (user_id) REFERENCES users(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                );
                """;

        stmt.execute(createOrders);

        String createBooksOrders = """
                CREATE TABLE IF NOT EXISTS books_orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    quantity INTEGER NOT NULL,
                    book_id INTEGER NOT NULL,
                    order_id INTEGER NOT NULL,
                
                    FOREIGN KEY (book_id) REFERENCES books(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                
                    FOREIGN KEY (order_id) REFERENCES orders(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                );
                """;

        stmt.execute(createBooksOrders);

        String createCarts = """
                CREATE TABLE IF NOT EXISTS carts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER UNIQUE NOT NULL,
                
                    FOREIGN KEY (user_id) REFERENCES users(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                );
                """;

        stmt.execute(createCarts);

        String createBooksCarts = """
                CREATE TABLE IF NOT EXISTS books_carts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER NOT NULL,
                    cart_id INTEGER NOT NULL,
                
                    FOREIGN KEY (book_id) REFERENCES books(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                
                    FOREIGN KEY (cart_id) REFERENCES carts(id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
                );
                """;

        stmt.execute(createBooksCarts);
    }

    private static void initRepositories() throws SQLException {
        RoleRepository.insertRoles();
        OrderStatusRepository.insertOrderStatuses();
    }
}
