package by.ny.server.service;

import by.ny.server.dao.CompanyDao;
import by.ny.server.entity.Company;

import java.util.List;

public class CompanyService {
    private CompanyDao companyDao;

    public CompanyService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public List<Company> listUsersCompanies(Integer userId) {
        return companyDao.listUsersCompanies(userId);
    }

    public Company getCompanyById(Integer id) {
        return companyDao.findCompanyById(id);
    }

    public boolean deleteCompany(Integer id) {
        return companyDao.deleteCompany(id);
    }

    public boolean saveCompany(Company company, Integer userId) {
        return companyDao.saveCompany(company, userId);
    }

}
