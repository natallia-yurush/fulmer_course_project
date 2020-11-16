package by.ny.server.controller.result.report;

import by.ny.server.entity.Report;

import java.io.Serializable;

public class SaveReportResult implements Serializable {
    private static final long serialVersionUID = 7724166489563471214L;

    private Report report;

    public SaveReportResult(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
