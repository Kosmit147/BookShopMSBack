package com.example;

import com.example.repositories.OrderStatusRepository;
import com.example.repositories.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for handling database connections and operations in the Bookshop application.
 * Provides methods for establishing connections, closing connections, creating necessary tables,
 * and initializing default data for roles and order statuses.
 * <p>
 * This class uses SQLite as the database and provides the ability to initialize the database schema
 * as well as interact with it (e.g., inserting default roles and order statuses).
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class DbConnection {
    private static final Logger logger = LogManager.getLogger(DbConnection.class);
    public static final String url = "jdbc:sqlite:bookshop.db";

    /**
     * Establishes a new database connection using the SQLite JDBC driver.
     *
     * @return a Connection object representing the connection to the database.
     * @throws SQLException if there is an error establishing the connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * Closes the provided database connection if it is not null.
     * Logs any SQLException that occurs during the closing process.
     *
     * @param connection the database connection to be closed.
     */
    public static void close(Connection connection) {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close database connection: {}", e.getMessage());
            }
        }
    }

    /**
     * Creates all necessary tables for the Bookshop application if they do not already exist.
     * The tables include books, roles, users, order statuses, orders, and related join tables (books_orders, books_carts, etc.).
     * <p>
     * This method also ensures foreign key constraints are enabled to maintain referential integrity.
     * </p>
     *
     * @param connection the database connection used to execute the SQL statements.
     * @throws SQLException if an error occurs while creating the tables.
     */
    public static void createTables(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");

        // Create books table
        String createBooks = """
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    price REAL NOT NULL
                );
                """;
        stmt.execute(createBooks);

        // Create roles table
        String createRoles = """
                CREATE TABLE IF NOT EXISTS roles (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL
                );
                """;
        stmt.execute(createRoles);

        // Create users table
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

        // Create order statuses table
        String createOrderStatuses = """
                CREATE TABLE IF NOT EXISTS order_statuses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL
                );
                """;
        stmt.execute(createOrderStatuses);

        // Create orders table
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

        // Create books_orders table (join table between books and orders)
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

        // Create carts table
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

        // Create books_carts table (join table between books and carts)
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

    /**
     * Initializes the database with default roles and order statuses by calling the appropriate methods
     * in the RoleRepository and OrderStatusRepository classes.
     *
     * @param connection the database connection used to insert default roles and order statuses.
     * @throws SQLException if an error occurs while inserting the default data.
     */
    public static void initRepositories(Connection connection) throws SQLException {
        RoleRepository.insertRoles(connection);
        OrderStatusRepository.insertOrderStatuses(connection);
    }
}
