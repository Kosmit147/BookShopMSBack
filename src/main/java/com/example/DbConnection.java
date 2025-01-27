package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    public static final String url = "jdbc:sqlite:bookshop.db";
    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database.");
            createTables();
            insertValues();
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
            System.out.println(e.toString());
        }
    }

    // TODO: either remove or reimplement these unused functions
    // public static int selectCartIdByUserId(int userId) throws SQLException {
    //     String selectCart = """
    //             SELECT id FROM carts WHERE user_id == ?;
    //             """;

    //     PreparedStatement stmt = connection.prepareStatement(selectCart);
    //     stmt.setInt(1, userId);
    //     ResultSet rs = stmt.executeQuery();

    //     int cartId = -1;

    //     if (rs.next())
    //         cartId = rs.getInt("id");

    //     return cartId;
    // }

    // private static int createCartForUser(int userId) throws SQLException {
    //     String createCart = """
    //             INSERT OR REPLACE INTO carts(user_id) VALUES(?);
    //             """;

    //     PreparedStatement stmt = connection.prepareStatement(createCart);
    //     stmt.setInt(1, userId);
    //     stmt.executeUpdate();

    //     return selectCartIdByUserId(userId);
    // }

    // private static void deleteAllBooksFromCart(int cartId) throws SQLException {
    //     String deleteBooks = """
    //             DELETE FROM books_carts WHERE cart_id = ?;
    //             """;

    //     PreparedStatement stmt = connection.prepareStatement(deleteBooks);
    //     stmt.setInt(1, cartId);
    //     stmt.executeUpdate();
    // }

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

        String createOrders = """
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    street TEXT NOT NULL,
                    city TEXT NOT NULL,
                    zip TEXT NOT NULL,
                    date TEXT NOT NULL,
                    status TEXT NOT NULL,
                    user_id INTEGER NOT NULL,
                
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

    private static void insertValues() throws SQLException {
        // TODO
        // String insertUserRole = """
        //         INSERT INTO roles(name) VALUES('user');
        //         """;

        // Statement stmt = connection.createStatement();
        // stmt.executeUpdate(insertUserRole);
    }
}
