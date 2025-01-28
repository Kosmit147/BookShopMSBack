package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;
import com.example.dto.Role;
import com.example.dto.RoleDto;

import java.sql.*;
import java.util.ArrayList;

public class RoleRepository {
    public static ArrayList<RoleDto> selectRoles() throws SQLException {
        Connection connection = DbConnection.getConnection();

        String selectRoles = """
                SELECT * FROM roles;
                """;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(selectRoles);

        ArrayList<RoleDto> result = new ArrayList<>();

        while (rs.next()) {
            Role role = Role.fromString(rs.getString("name"));
            result.add(new RoleDto(rs.getInt("id"), role));
        }

        return result;
    }

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
