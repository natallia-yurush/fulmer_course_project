package by.ny.server.dao;

import by.ny.server.entity.User;
import by.ny.server.entity.UserRole;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao {
    private UserDao() {
    }

    private static class Holder {
        public static UserDao instance = new UserDao();
    }

    public static UserDao getInstance() {
        return Holder.instance;
    }


    public User findUserByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, surname, name, login, password, phone_number, role, email " +
                            "FROM user WHERE login=?");
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), UserRole.valueOf(resultSet.getString(7).toUpperCase()),
                        resultSet.getString(8));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUserByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, surname, name, login, password, phone_number, role, email " +
                            "FROM user WHERE email=?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), UserRole.valueOf(resultSet.getString(7).toUpperCase()),
                        resultSet.getString(8));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> listUsers() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, surname, name, login, password, phone_number, role, email FROM user WHERE role = \'CLIENT\'");
            while (resultSet.next()) {
                result.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), UserRole.valueOf(resultSet.getString(7).toUpperCase()),
                        resultSet.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User findUserById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, surname, name, login, password, phone_number, role, email " +
                            "FROM user WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), UserRole.valueOf(resultSet.getString(7).toUpperCase()),
                        resultSet.getString(8));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUser(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveUser(User user) {
        try {
            PreparedStatement statement;
            if (user.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO user (surname, name, login, password, " +
                        "phone_number, role, email) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, user.getSurname());
                statement.setString(2, user.getName());
                statement.setString(3, user.getLogin());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getPhoneNumber());
                statement.setString(6, user.getRole().toString());
                statement.setString(7, user.getEmail());
            } else {
                statement = connection.prepareStatement("UPDATE user SET surname=?, name=?, login=?, password=?, " +
                        "phone_number=?, role=?, email=? WHERE id=?");
                statement.setString(1, user.getSurname());
                statement.setString(2, user.getName());
                statement.setString(3, user.getLogin());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getPhoneNumber());
                statement.setString(6, user.getRole().toString());
                statement.setString(7, user.getEmail());
                statement.setInt(8, user.getId());
            }
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
