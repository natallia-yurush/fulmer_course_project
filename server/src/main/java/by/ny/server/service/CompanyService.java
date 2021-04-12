package by.ny.server.service;

import by.ny.server.dao.CompanyDao;
import by.ny.server.entity.Company;

import java.util.List;

public class CompanyService {
    private CompanyService() {
    }

    private CompanyDao companyDao = new CompanyDao();

    public List<Company> listUsersCompanies(Integer userId) {
        return companyDao.findAllUsersCompanies(userId);
    }

    public List<Company> listCompanies() {
        return companyDao.findAll();
    }

    public Company getCompanyById(Integer id) {
        return companyDao.findById(id);
    }

    public boolean deleteCompany(Integer id) {
        return companyDao.delete(id);
    }

    public boolean saveCompany(Company company) {
        return companyDao.save(company);
    }

    private static class Holder {
        public static CompanyService instance = new CompanyService();
    }

    public static CompanyService getInstance() {
        return Holder.instance;
    }
}
