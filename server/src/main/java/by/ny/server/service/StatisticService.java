package by.ny.server.service;

public class StatisticService {
    private StatisticService() {
    }

    public int numberOfUsersResult() {
        UserService userService = UserService.getInstance();
        return userService.listUsers().size();
    }

    public int numberOfCompaniesResult() {
        CompanyService companyService = CompanyService.getInstance();
        return companyService.listCompanies().size();
    }

    public int numberOfReportsResult() {
        ReportService reportService = ReportService.getInstance();
        return reportService.listReports().size();
    }

    private static class Holder {
        public static StatisticService instance = new StatisticService();
    }

    public static StatisticService getInstance() {
        return Holder.instance;
    }
}
