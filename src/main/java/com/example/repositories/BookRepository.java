package com.example.repositories;

import com.example.NotFoundException;
import com.example.dto.BookDto;
import com.example.dto.NewBookDto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BookRepository {
    public static void addBook(Connection connection, NewBookDto book) throws SQLException {
        String addBook = """
                INSERT INTO books(title, author, price) VALUES(?, ?, ?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);

        stmt.setString(1, book.title);
        stmt.setString(2, book.author);
        stmt.setBigDecimal(3, book.price);

        stmt.executeUpdate();
    }

    public static BookDto selectBookById(Connection connection, int id) throws SQLException, NotFoundException {
        String selectBook = """
                SELECT * FROM books WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectBook);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int bookId = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            BigDecimal price = rs.getBigDecimal("price");

            return new BookDto(bookId, title, author, price);
        }

        throw new NotFoundException();
    }

    public static ArrayList<BookDto> selectBooks(Connection connection) throws SQLException {
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

    public static void deleteBook(Connection connection, int bookId) throws SQLException, NotFoundException {
        String deleteBook = """
                DELETE FROM books WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(deleteBook);
        stmt.setInt(1, bookId);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows < 1)
            throw new NotFoundException();
    }

    public static void updateBook(Connection connection, BookDto book) throws SQLException, NotFoundException {
        String updateBook = """
                UPDATE books
                    SET title = ?, author = ?, price = ?
                    WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(updateBook);
        stmt.setString(1, book.title);
        stmt.setString(2, book.author);
        stmt.setBigDecimal(3, book.price);
        stmt.setInt(4, book.id);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected < 1)
            throw new NotFoundException();
    }
}