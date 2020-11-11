package by.ny.server.controller.result;

import java.io.Serializable;

public class StatisticResult implements Serializable {
    private static final long serialVersionUID = 3606454483849255736L;

    private int numberOfUsers;
    private int numberOfCompanies;
    private int numberOfReports;

    public StatisticResult(int numberOfUsers, int numberOfCompanies, int numberOfReports) {
        this.numberOfUsers = numberOfUsers;
        this.numberOfCompanies = numberOfCompanies;
        this.numberOfReports = numberOfReports;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public int getNumberOfCompanies() {
        return numberOfCompanies;
    }

    public void setNumberOfCompanies(int numberOfCompanies) {
        this.numberOfCompanies = numberOfCompanies;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
