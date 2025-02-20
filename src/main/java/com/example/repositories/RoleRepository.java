package com.example.repositories;

import com.example.NotFoundException;
import com.example.dto.Role;

import java.sql.*;

public class RoleRepository {
    public static void insertRoles(Connection connection) throws SQLException {
        String insertIntoRoles = """
                INSERT INTO roles (name)
                SELECT 'User'
                WHERE NOT EXISTS (
                    SELECT 1 FROM roles WHERE name = 'User'
                );
                
                INSERT INTO roles (name)
                SELECT 'Admin'
                WHERE NOT EXISTS (
                    SELECT 1 FROM roles WHERE name = 'Admin'
                );
                """;

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(insertIntoRoles);
    }

    public static Role selectRoleById(Connection connection, int roleId) throws SQLException, NotFoundException {
        String selectRoleName = """
                SELECT name FROM roles WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectRoleName);
        stmt.setInt(1, roleId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return Role.fromString(rs.getString("name"));
        }

        throw new NotFoundException();
    }

    public static int selectRoleId(Connection connection, Role role) throws SQLException, NotFoundException {
        String selectRoleId = """
                SELECT id FROM roles WHERE name = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectRoleId);
        stmt.setString(1, role.toString());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        throw new NotFoundException();
    }
}
