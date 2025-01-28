package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;
import com.example.dto.*;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    public static void addUser(NewUserDto user) throws SQLException, NotFoundException {
        addUserWithRole(new NewUserWithRoleDto(user.name, user.email, user.password, Role.initialUserRole));
    }

    public static void addUserWithRole(NewUserWithRoleDto user) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        int roleId = RoleRepository.selectRoleId(user.role);

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

    public static UserDto selectUserById(int id) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

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
            Role role = RoleRepository.selectRoleById(rs.getInt("role_id"));

            return new UserDto(userId, name, email, password, role);
        }

        throw new NotFoundException();
    }

    public static UserDto selectUserForLogin(UserLoginDto loginData) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

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
            Role role = RoleRepository.selectRoleById(rs.getInt("role_id"));

            return new UserDto(userId, name, email, password, role);
        }

        throw new NotFoundException();
    }

    public static ArrayList<UserDto> selectUsers() throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

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
            Role role = RoleRepository.selectRoleById(rs.getInt("role_id"));

            result.add(new UserDto(id, name, email, password, role));
        }

        return result;
    }

    public static void deleteUser(int userId) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        String deleteBook = """
                DELETE FROM users WHERE id = ?;
                """;

        PreparedStatement stmt = connection.prepareStatement(deleteBook);
        stmt.setInt(1, userId);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows < 1)
            throw new NotFoundException();
    }

    public static void updateUser(UserDto user) throws SQLException, NotFoundException {
        Connection connection = DbConnection.getConnection();

        int roleId = RoleRepository.selectRoleId(user.role);

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
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected < 1)
            throw new NotFoundException();
    }
}
