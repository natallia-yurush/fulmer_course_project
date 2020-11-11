package by.ny.server.controller.command.company;

import by.ny.server.entity.Company;

import java.io.Serializable;

public class SaveCompanyCommand implements Serializable {
    private static final long serialVersionUID = 6283348051165880740L;

    private Company company;

    public SaveCompanyCommand(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
