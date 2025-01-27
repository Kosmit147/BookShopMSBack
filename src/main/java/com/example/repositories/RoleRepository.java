package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;
import com.example.dto.NewRoleDto;
import com.example.dto.RoleDto;

import java.sql.*;
import java.util.ArrayList;

public class RoleRepository {
    public static void addRole(NewRoleDto role) throws SQLException {
        Connection connection = DbConnection.getConnection();

        String addBook = """
                INSERT INTO roles(name) VALUES(?);
                """;

        PreparedStatement stmt = connection.prepareStatement(addBook);
        stmt.setString(1, role.name);
        stmt.executeUpdate();
    }

    public static ArrayList<RoleDto> selectRoles() throws SQLException {
        Connection connection = DbConnection.getConnection();

        String selectRoles = """
                SELECT * FROM roles;
                """;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(selectRoles);

        ArrayList<RoleDto> result = new ArrayList<>();

        while (rs.next()) {
            result.add(new RoleDto(rs.getInt("id"), rs.getString("name")));
        }

        return result;
    }

    public static String selectRoleNameById(int roleId) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        String selectRoleName = """
                SELECT name FROM roles WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectRoleName);
        stmt.setInt(1, roleId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("name");
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
