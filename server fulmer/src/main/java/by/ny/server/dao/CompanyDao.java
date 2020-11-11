package by.ny.server.dao;

import by.ny.server.entity.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao extends AbstractDao {
    private CompanyDao() {
    }

    private static class Holder {
        public static CompanyDao instance = new CompanyDao();
    }

    public static CompanyDao getInstance() {
        return CompanyDao.Holder.instance;
    }

    UserDao userDao = UserDao.getInstance();

    public Company findCompanyById(Integer id) {
        try {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, name, address, email, phone_number, id_user " +
                            "FROM company WHERE id=?")) {
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();
            }

            if (resultSet.next()) {
                return new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), userDao.findUserById(resultSet.getInt(6)));
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
                    .prepareStatement("SELECT id, name, address, email, phone_number, id_user FROM company WHERE id_user = ?");
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), userDao.findUserById(resultSet.getInt(6))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean saveCompany(Company company) {
        try {
            PreparedStatement statement;
            if (company.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO company (name, address, email, phone_number, id_user) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, company.getName());
                statement.setString(2, company.getAddress());
                statement.setString(3, company.getEmail());
                statement.setString(4, company.getPhoneNumber());
                statement.setInt(5, company.getUser().getId());
            } else {
                statement = connection.prepareStatement("UPDATE company SET name=?, address=?, email=?, phone_number=?, id_user=? WHERE id=?");
                statement.setString(1, company.getName());
                statement.setString(2, company.getAddress());
                statement.setString(3, company.getEmail());
                statement.setString(4, company.getPhoneNumber());
                statement.setInt(5, company.getUser().getId());
                statement.setInt(6, company.getId());
            }
            statement.execute();
            return true;
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
