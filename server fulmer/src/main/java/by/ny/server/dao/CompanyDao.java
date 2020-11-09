package by.ny.server.dao;

import by.ny.server.entity.Company;
import by.ny.server.entity.User;
import by.ny.server.entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao extends AbstractDao {

    public Company findCompanyById(Integer id) {
        try {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, name, address, email, phone_number " +
                            "FROM company WHERE id=?")) {
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();
            }

            if (resultSet.next()) {
                return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Company> listUsersCompanies(Integer userId) {
        List<Company> result = new ArrayList<>();
        try {
            ResultSet resultSet;
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id_company FROM user_company WHERE id_company = ?");
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(findCompanyById(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //TODO!!!!!!!!!!!!
    public boolean saveCompany(Company company, Integer userId) {
        try {
            PreparedStatement statement;
            if (company.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO company (name, address, email, phone_number) VALUES (?, ?, ?, ?)");
                statement.setString(1, company.getName());
                statement.setString(2, company.getAddress());
                statement.setString(3, company.getEmail());
                statement.setString(4, company.getPhoneNumber());
                statement.execute();

                statement = connection.prepareStatement("INSERT INTO user_company (id_user, id_company) VALUES (?, ?)");
                statement.setInt(1, userId);
                statement.setInt(2, company.getId());//TODO у компании еще нет id

                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCompany(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM company WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
