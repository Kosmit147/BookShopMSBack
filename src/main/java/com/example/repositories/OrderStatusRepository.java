package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;

import java.sql.*;

public class OrderStatusRepository {
    public static OrderStatus selectOrderStatusById(int id) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        String selectOrderStatus = """
                SELECT name FROM order_statuses WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectOrderStatus);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String orderStatusStr = rs.getString("name");
            return OrderStatus.fromString(orderStatusStr);
        }

        throw new NotFoundException();
    }

    public static int selectOrderStatusId(OrderStatus status) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        String selectOrderStatusId = """
                SELECT id FROM order_statuses WHERE name = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectOrderStatusId);
        stmt.setString(1, status.toString());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        throw new NotFoundException();
    }
}
