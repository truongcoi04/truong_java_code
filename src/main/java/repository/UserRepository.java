package repository;

import config.ConnectionProvider;
import entity.Role;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static List<User> findAll() {
        String sql = "select * from account_user";
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                int exInYear = resultSet.getInt("exp_in_year");
                String proSkill = resultSet.getString("proskill");
                User user = new User(userId, fullName, email, password, Role.valueOf(role), exInYear, proSkill);
                users.add(user);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    public static User findById(int userId) {
        String sql = "SELECT * FROM account_user where id = ?";
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                int exInYear = resultSet.getInt("exp_in_year");
                String proSkill = resultSet.getString("proskill");
                User user = new User(userId, fullName, email, password, Role.valueOf(role), exInYear, proSkill);
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean deleteUser(int userId) {
        String sql = "delete from account_user where id = ?";
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            int deleted = statement.executeUpdate();
            if (deleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }


    public static boolean createUser(User user) {
        String sql = "insert into account_user( full_name, email, password, role, exp_in_year, proSkill )\n" +
                "values ( ?, ?, ?, ?, ?, ?) ";
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().toString());
            if (user.getRole() == Role.ADMIN) {
                statement.setInt(5, user.getExpInYear());
                statement.setObject(6, null);
            } else {
                statement.setObject(5, null);
                statement.setString(6, user.getProSkill());
            }


            int createUpdate = statement.executeUpdate();
            if (createUpdate > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;


    }

}
