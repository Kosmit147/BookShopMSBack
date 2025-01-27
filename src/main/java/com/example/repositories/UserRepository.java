package com.example.repositories;

import com.example.DbConnection;
import com.example.NotFoundException;
import com.example.dto.NewUserDto;
import com.example.dto.NewUserWithRoleDto;
import com.example.dto.UserDto;
import com.example.dto.UserLoginDto;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    public static void addUser(NewUserDto user) throws SQLException {
        // TODO: don't hardcode the role string like this
        String role = "user";
        addUserWithRole(new NewUserWithRoleDto(user.name, user.email, user.password, role));
    }

    public static void addUserWithRole(NewUserWithRoleDto user) throws SQLException {
        Connection connection = DbConnection.getConnection();

        int roleId = RoleRepository.selectRoleIdByName(user.role);

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
            String roleName = RoleRepository.selectRoleNameById(rs.getInt("role_id"));

            return new UserDto(userId, name, email, password, roleName);
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
            String roleName = RoleRepository.selectRoleNameById(rs.getInt("role_id"));

            return new UserDto(userId, name, email, password, roleName);
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
            String roleName = RoleRepository.selectRoleNameById(rs.getInt("role_id"));

            result.add(new UserDto(id, name, email, password, roleName));
        }

        return result;
    }

    // TODO: delete or reimplement
    // public static int selectUserIdByEmail(String userEmail) throws SQLException {
    //     Connection connection = DbConnection.getConnection();

    //     String selectUserId = """
    //             SELECT id FROM users WHERE email == ?;
    //             """;

    //     PreparedStatement stmt = connection.prepareStatement(selectUserId);
    //     stmt.setString(1, userEmail);
    //     ResultSet rs = stmt.executeQuery();
    //     return rs.getInt("id");
    // }

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
}
