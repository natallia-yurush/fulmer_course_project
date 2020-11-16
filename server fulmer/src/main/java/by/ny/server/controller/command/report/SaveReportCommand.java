package by.ny.server.controller.command.report;

import by.ny.server.entity.Report;

import java.io.Serializable;

public class SaveReportCommand implements Serializable {
    private static final long serialVersionUID = -8915086200986937808L;

    private Report report;

    public SaveReportCommand(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
