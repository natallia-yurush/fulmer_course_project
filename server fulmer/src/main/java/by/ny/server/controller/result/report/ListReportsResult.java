package by.ny.server.controller.result.report;

import by.ny.server.entity.Report;

import java.io.Serializable;
import java.util.List;

public class ListReportsResult implements Serializable {
    private static final long serialVersionUID = 1692777986577865221L;

    private List<Report> reports;

    public ListReportsResult(List<Report> reports) {
        this.reports = reports;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
