package com.example.repositories;

import com.example.NotFoundException;
import com.example.dto.*;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    public static void addUser(Connection connection, NewUserDto user) throws SQLException, NotFoundException {
        addUserWithRole(connection, new NewUserWithRoleDto(user.name, user.email, user.password, Role.initialUserRole));
    }

    public static void addUserWithRole(Connection connection, NewUserWithRoleDto user) throws SQLException, NotFoundException {
        int roleId = RoleRepository.selectRoleId(connection, user.role);

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

    public static UserDto selectUserById(Connection connection, int id) throws SQLException, NotFoundException {
        String selectUser = """
                SELECT * FROM users WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectUser);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Role role = RoleRepository.selectRoleById(connection, rs.getInt("role_id"));

            return new UserDto(userId, name, email, password, role);
        }

        throw new NotFoundException();
    }

    public static UserDto selectUserForLogin(Connection connection, UserLoginDto loginData) throws SQLException, NotFoundException {
        String selectUser = """
                SELECT * FROM users WHERE email = ? AND password = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(selectUser);
        stmt.setString(1, loginData.email);
        stmt.setString(2, loginData.password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Role role = RoleRepository.selectRoleById(connection, rs.getInt("role_id"));

            return new UserDto(userId, name, email, password, role);
        }

        throw new NotFoundException();
    }

    public static ArrayList<UserDto> selectUsers(Connection connection) throws SQLException, NotFoundException {
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
            Role role = RoleRepository.selectRoleById(connection, rs.getInt("role_id"));

            result.add(new UserDto(id, name, email, password, role));
        }

        return result;
    }

    public static void deleteUser(Connection connection, int userId) throws SQLException, NotFoundException {
        String deleteBook = """
                DELETE FROM users WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(deleteBook);
        stmt.setInt(1, userId);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows < 1)
            throw new NotFoundException();
    }

    public static void updateUser(Connection connection, UserDto user) throws SQLException, NotFoundException {
        int roleId = RoleRepository.selectRoleId(connection, user.role);

        String updateUser = """
                UPDATE users
                    SET name = ?, email = ?, password = ?, role_id = ?
                    WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(updateUser);
        stmt.setString(1, user.name);
        stmt.setString(2, user.email);
        stmt.setString(3, user.password);
        stmt.setInt(4, roleId);
        stmt.setInt(5, user.id);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected < 1)
            throw new NotFoundException();
    }
}
