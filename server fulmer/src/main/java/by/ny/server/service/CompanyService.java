package by.ny.server.service;

import by.ny.server.dao.CompanyDao;
import by.ny.server.entity.Company;

import java.util.List;

public class CompanyService {
    private CompanyService() {
    }

    private CompanyDao companyDao = CompanyDao.getInstance();

    public List<Company> listUsersCompanies(Integer userId) {
        return companyDao.listUsersCompanies(userId);
    }

    public List<Company> listCompanies() {
        return companyDao.listCompanies();
    }

    public Company getCompanyById(Integer id) {
        return companyDao.findCompanyById(id);
    }

    public boolean deleteCompany(Integer id) {
        return companyDao.deleteCompany(id);
    }

    public boolean saveCompany(Company company) {
        return companyDao.saveCompany(company);
    }

    private static class Holder {
        public static CompanyService instance = new CompanyService();
    }

    public static CompanyService getInstance() {
        return CompanyService.Holder.instance;
    }
}
