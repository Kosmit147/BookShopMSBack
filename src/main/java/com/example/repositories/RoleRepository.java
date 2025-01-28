package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;
import com.example.dto.Role;

import java.sql.*;

public class RoleRepository {
    public static Role selectRoleById(int roleId) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

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

    public static int selectRoleId(Role role) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        String selectRoleId = """
                SELECT id FROM roles WHERE name == ?;
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
