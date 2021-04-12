package by.ny.server.dao;

import by.ny.server.entity.User;
import by.ny.server.entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User>{

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.getConnection().createStatement();
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

    public User findUserByLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.getConnection()
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
            PreparedStatement preparedStatement = connection.getConnection()
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

    @Override
    public User findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.getConnection()
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

    @Override
    public boolean delete(Integer id) {
        try {
            PreparedStatement statement = connection.getConnection().prepareStatement("DELETE FROM user WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement statement;
            if (user.getId() == null) {
                statement = connection.getConnection().prepareStatement("INSERT INTO user (surname, name, login, password, " +
                        "phone_number, role, email) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, user.getSurname());
                statement.setString(2, user.getName());
                statement.setString(3, user.getLogin());
                statement.setString(4, user.getPassword());
                statement.setString(5, user.getPhoneNumber());
                statement.setString(6, user.getRole().toString());
                statement.setString(7, user.getEmail());
            } else {
                statement = connection.getConnection().prepareStatement("UPDATE user SET surname=?, name=?, login=?, password=?, " +
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
