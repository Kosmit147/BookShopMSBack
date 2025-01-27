package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepository {
    public static String selectRoleNameById(int roleId) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        String selectUser = """
                SELECT name FROM roles WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectUser);
        stmt.setInt(1, roleId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            return name;
        }

        throw new NotFoundException();
    }

    public static int selectRoleIdByName(String roleName) throws SQLException {
        Connection connection = DbConnection.getConnection();

        String selectRoleId = """
                SELECT id FROM roles WHERE name == ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectRoleId);
        stmt.setString(1, roleName);
        ResultSet rs = stmt.executeQuery();
        return rs.getInt("id");
    }
}
