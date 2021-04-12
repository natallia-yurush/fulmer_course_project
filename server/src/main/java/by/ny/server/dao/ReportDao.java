package by.ny.server.dao;

import by.ny.server.entity.Currency;
import by.ny.server.entity.DollarRate;
import by.ny.server.entity.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao extends AbstractDao<Report>{
    CompanyDao companyDao = new CompanyDao();
    DollarRateDao dollarRateDao = new DollarRateDao();

    @Override
    public Report findById(Integer id) {
        try {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("SELECT id, id_company, id_dollar_rate, date, x1, x2, x3, x4, x5, x6, x7, x8, x9, H, currency " +
                            "FROM report WHERE id=?")) {
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();
            }

            if (resultSet.next()) {
                return new Report(resultSet.getInt(1), companyDao.findById(resultSet.getInt(2)),
                        dollarRateDao.findById(resultSet.getInt(3)),
                        resultSet.getDate(4), resultSet.getDouble(5), resultSet.getDouble(6),
                        resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
                        resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
                        resultSet.getDouble(13), resultSet.getDouble(14), Currency.valueOf(resultSet.getString(15).toUpperCase()));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Report> findReportsByDate(java.util.Date date) {
        List<Report> result = new ArrayList<>();
        try {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("SELECT id, id_company, id_dollar_rate, date, x1, x2, x3, x4, x5, x6, x7, x8, x9, H, currency " +
                            "FROM report WHERE date=?")) {
                preparedStatement.setDate(1, new Date(date.getTime()));

                resultSet = preparedStatement.executeQuery();
            }

            while (resultSet.next()) {
                result.add(new Report(resultSet.getInt(1), companyDao.findById(resultSet.getInt(2)),
                        dollarRateDao.findById(resultSet.getInt(3)),
                        resultSet.getDate(4), resultSet.getDouble(5), resultSet.getDouble(6),
                        resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
                        resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
                        resultSet.getDouble(13), resultSet.getDouble(14), Currency.valueOf(resultSet.getString(15).toUpperCase())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Report> findAll() {
        List<Report> result = new ArrayList<>();
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, id_company, id_dollar_rate, date, x1, x2, x3, x4, x5, x6, x7, x8, x9, H, currency" +
                    " FROM report");
            while (resultSet.next()) {
                result.add(new Report(resultSet.getInt(1), companyDao.findById(resultSet.getInt(2)),
                        dollarRateDao.findById(resultSet.getInt(3)),
                        resultSet.getDate(4), resultSet.getDouble(5), resultSet.getDouble(6),
                        resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
                        resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
                        resultSet.getDouble(13), resultSet.getDouble(14), Currency.valueOf(resultSet.getString(15).toUpperCase())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean save(Report entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public Report saveReport(Report report) {
        try {
            PreparedStatement statement;
            if (report.getId() == null) {
                statement = connection.getConnection().prepareStatement("INSERT INTO report " +
                        "(id_company, id_dollar_rate, date, x1, x2, x3, x4, x5, x6, x7, x8, x9, H, currency) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setInt(1, report.getCompany().getId());
                statement.setInt(2, dollarRateDao.findLastDollarRate().getId());
                statement.setDate(3, new Date(new java.util.Date().getTime()));
                statement.setDouble(4, report.getX1());
                statement.setDouble(5, report.getX2());
                statement.setDouble(6, report.getX3());
                statement.setDouble(7, report.getX4());
                statement.setDouble(8, report.getX5());
                statement.setDouble(9, report.getX6());
                statement.setDouble(10, report.getX7());
                statement.setDouble(11, report.getX8());
                statement.setDouble(12, report.getX9());
                Double calcResult = calculateResult(report.getCurrency(), report.getX1(), report.getX2(), report.getX3(), report.getX4(), report.getX5(), report.getX6(), report.getX7(), report.getX8(), report.getX9());
                statement.setDouble(13, calcResult);
                statement.setString(14, report.getCurrency().toString());
                statement.execute();
                return findLastReport();
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double calculateResult(Currency currency, double x1, double x2, double x3, double x4, double x5, double x6, double x7, double x8, double x9) {
        DollarRate dollarRate = dollarRateDao.findLastDollarRate();
        double coef = 1;
        if (currency == Currency.BYN) {
            coef = dollarRate.getBYN();
        } else if (currency == Currency.EUR) {
            coef = dollarRate.getEUR();
        }
        return 5.529 * (x1 * coef) + 0.212 * (coef * x2) + 0.073 * (coef * x3) +
                1.27 * (coef * x4) + 0.12 * (coef * x5) + 2.235 * (coef * x6) + 0.575 * (coef * x7) +
                1.083 * (coef * x8) + 0.984 * (coef * x9) - 6.075;
    }

    public Report findLastReport() {
        try {
            PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("SELECT id, id_company, id_dollar_rate, date, x1, x2, x3, x4, x5, x6, x7, x8, x9, H, currency " +
                            "FROM report ORDER BY id DESC LIMIT 1");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Report(resultSet.getInt(1), companyDao.findById(resultSet.getInt(2)),
                        dollarRateDao.findById(resultSet.getInt(3)),
                        resultSet.getDate(4), resultSet.getDouble(5), resultSet.getDouble(6),
                        resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
                        resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
                        resultSet.getDouble(13), resultSet.getDouble(14), Currency.valueOf(resultSet.getString(15).toUpperCase()));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Report> listCompaniesReports(Integer companyId) {
        List<Report> result = new ArrayList<>();
        try {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement("SELECT id, id_company, id_dollar_rate, date, x1, x2, x3, x4, x5, x6, x7, x8, x9, H, currency " +
                            "FROM report WHERE id_company = ?")) {
                preparedStatement.setInt(1, companyId);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result.add(new Report(resultSet.getInt(1), companyDao.findById(resultSet.getInt(2)),
                            dollarRateDao.findById(resultSet.getInt(3)),
                            resultSet.getDate(4), resultSet.getDouble(5), resultSet.getDouble(6),
                            resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
                            resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
                            resultSet.getDouble(13), resultSet.getDouble(14), Currency.valueOf(resultSet.getString(15).toUpperCase())));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
