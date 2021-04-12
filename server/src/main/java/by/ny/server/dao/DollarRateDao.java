package by.ny.server.dao;

import by.ny.server.entity.DollarRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DollarRateDao extends AbstractDao<DollarRate>{

    public DollarRate findLastDollarRate() {
        try {
            PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("SELECT id, date, EUR, BYN " +
                            "FROM dollar_rate ORDER BY id DESC LIMIT 1");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new DollarRate(resultSet.getInt(1), new java.util.Date(resultSet.getDate(2).getTime()),
                        resultSet.getDouble(3), resultSet.getDouble(4));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DollarRate> findAll() {
        List<DollarRate> result = new ArrayList<>();
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, date, EUR, BYN FROM dollar_rate");
            while (resultSet.next()) {
                result.add(new DollarRate(resultSet.getInt(1), resultSet.getDate(2),
                        resultSet.getDouble(3), resultSet.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean save(DollarRate dollarRate) {
        try {
            PreparedStatement statement;
            if (dollarRate.getId() == null) {
                statement = connection.getConnection().prepareStatement("INSERT INTO dollar_rate (date, EUR, BYN) VALUES (?, ?, ?)");
                statement.setDate(1, new Date(dollarRate.getDate().getTime()));
                statement.setDouble(2, dollarRate.getEUR());
                statement.setDouble(3, dollarRate.getBYN());
                statement.execute();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public DollarRate findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("SELECT id, date, EUR, BYN FROM dollar_rate WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new DollarRate(resultSet.getInt(1), resultSet.getDate(2),
                        resultSet.getDouble(3), resultSet.getDouble(4));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
