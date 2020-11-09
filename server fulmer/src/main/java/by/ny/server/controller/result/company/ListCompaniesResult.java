package by.ny.server.controller.result.company;

import by.ny.server.entity.Company;

import java.io.Serializable;
import java.util.List;

public class ListCompaniesResult implements Serializable {
    private static final long serialVersionUID = -774317365049809265L;

    private List<Company> companies;

    public ListCompaniesResult(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
