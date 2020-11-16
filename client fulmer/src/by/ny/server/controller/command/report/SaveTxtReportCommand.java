package by.ny.server.controller.command.report;

import by.ny.server.entity.Report;

import java.io.Serializable;

public class SaveTxtReportCommand implements Serializable {
    private static final long serialVersionUID = 2097591334980633881L;

    private Report report;

    public SaveTxtReportCommand(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
