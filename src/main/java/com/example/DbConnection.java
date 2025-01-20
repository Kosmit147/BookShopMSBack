package com.example;

import com.example.dto.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DbConnection {
    public static final String url = "jdbc:sqlite:bookshop.db";

    Connection connection;

    DbConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to database.");
        createTables();
        insertValues();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void addBook(NewBookDto book) throws SQLException {
        String addBook = """
                INSERT INTO books(title, author, price) VALUES(?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);

        stmt.setString(1, book.title);
        stmt.setString(2, book.author);
        stmt.setBigDecimal(3, book.price);

        stmt.executeUpdate();
    }

    public void addUser(NewUserDto user) throws SQLException {
        String role = "user";
        addUserWithRole(new NewUserWithRoleDto(user.name, user.email, user.password, role));
    }

    public void addUserWithRole(NewUserWithRoleDto user) throws SQLException {
        int roleId = selectRoleIdByName(user.role);

        String addBook = """
                INSERT INTO users(name, email, password, role_id) VALUES(?, ?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);

        stmt.setString(1, user.name);
        stmt.setString(2, user.email);
        stmt.setString(3, user.password);
        stmt.setInt(4, roleId);

        stmt.executeUpdate();
    }

    public void addOrder(NewOrderDto order) throws SQLException {
        int userId = selectUserIdByEmail(order.userEmail);

        String addOrder = """
                INSERT INTO orders(first_name, last_name, street, city, zip, date, user_id) VALUES(?, ?, ?, ?, ?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addOrder);

        stmt.setString(1, order.firstName);
        stmt.setString(2, order.lastName);
        stmt.setString(3, order.street);
        stmt.setString(4, order.city);
        stmt.setString(5, order.zip);
        stmt.setString(6, order.date);
        stmt.setInt(7, userId);

        stmt.executeUpdate();
    }

    public BookDto selectBookById(int bookId) throws SQLException, NotFoundException {
        String selectBooks = """
                SELECT * FROM books WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectBooks);
        stmt.setInt(1, bookId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            BigDecimal price = rs.getBigDecimal("price");

            return new BookDto(id, title, author, price);
        }

        throw new NotFoundException();
    }

    public ArrayList<BookDto> selectBooks() throws SQLException {
        String selectBooks = """
                SELECT * FROM books;
                """;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(selectBooks);

        ArrayList<BookDto> result = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            BigDecimal price = rs.getBigDecimal("price");

            result.add(new BookDto(id, title, author, price));
        }

        return result;
    }

    public ArrayList<UserDto> selectUsers() throws SQLException {
        String selectUsers = """
                SELECT * FROM users;
                """;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(selectUsers);

        ArrayList<UserDto> result = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");

            result.add(new UserDto(id, name, email, password));
        }

        return result;
    }

    public ArrayList<Integer> selectBookIdsByTitles(String[] titles) throws SQLException {
        if (titles.length == 0) {
            return new ArrayList<>();
        }

        StringBuilder selectBookIds = new StringBuilder("""
                SELECT id FROM books WHERE title IN (
                """);

        for (int i = 0; i < titles.length; i++) {
            selectBookIds.append("?");

            if (i < titles.length - 1)
                selectBookIds.append(", ");
        }

        selectBookIds.append(");");
        PreparedStatement stmt = connection.prepareStatement(selectBookIds.toString());

        for (int i = 0; i < titles.length; i++)
            stmt.setString(i, titles[i]);

        ResultSet rs = stmt.executeQuery();
        ArrayList<Integer> result = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            result.add(id);
        }

        return result;
    }

    public int selectRoleIdByName(String roleName) throws SQLException {
        String selectRoleId = """
                SELECT id FROM roles WHERE name == ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectRoleId);
        stmt.setString(1, roleName);
        ResultSet rs = stmt.executeQuery();
        return rs.getInt("id");
    }

    public int selectUserIdByEmail(String userEmail) throws SQLException {
        String selectUserId = """
                SELECT id FROM users WHERE email == ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectUserId);
        stmt.setString(1, userEmail);
        ResultSet rs = stmt.executeQuery();
        return rs.getInt("id");
    }

    public int selectCartIdByUserId(int userId) throws SQLException {
        String selectCartId = """
                SELECT id FROM carts WHERE user_id == ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectCartId);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        int cartId = -1;

        if (rs.next())
            cartId = rs.getInt("id");

        return cartId;
    }

    private int createCartForUser(int userId) throws SQLException {
        String createCart = """
                INSERT OR REPLACE INTO carts(user_id) VALUES(?);
                """;

        PreparedStatement stmt = connection.prepareStatement(createCart);
        stmt.setInt(1, userId);
        stmt.executeUpdate();

        return selectCartIdByUserId(userId);
    }

    private void deleteAllBooksFromCart(int cartId) throws SQLException {
        String deleteBooks = """
                DELETE FROM books_carts WHERE cart_id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(deleteBooks);
        stmt.setInt(1, cartId);
        stmt.executeUpdate();
    }

    private void createTables() throws SQLException {
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

    private void insertValues() throws SQLException {
        String insertUserRole = """
                INSERT OR REPLACE INTO roles(name) VALUES('user');
                """;

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(insertUserRole);
    }
}
