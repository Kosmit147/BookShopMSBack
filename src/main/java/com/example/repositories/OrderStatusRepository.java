package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;
import com.example.dto.OrderStatus;

import java.sql.*;

public class OrderStatusRepository {
    public static void insertOrderStatuses() throws SQLException {
        Connection connection = DbConnection.getConnection();

        String insertIntoOrderStatuses = """
                INSERT INTO order_statuses (name)
                SELECT 'InPreparation'
                WHERE NOT EXISTS (
                    SELECT 1 FROM order_statuses WHERE name = 'InPreparation'
                );
                
                INSERT INTO order_statuses (name)
                SELECT 'Sent'
                WHERE NOT EXISTS (
                    SELECT 1 FROM order_statuses WHERE name = 'Sent'
                );
                
                INSERT INTO order_statuses (name)
                SELECT 'Delivered'
                WHERE NOT EXISTS (
                    SELECT 1 FROM order_statuses WHERE name = 'Delivered'
                );
                """;

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(insertIntoOrderStatuses);
    }

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
