package by.ny.server.controller.command.report;

import by.ny.server.entity.Report;

import java.io.Serializable;

public class SavePdfReportCommand implements Serializable {
    private static final long serialVersionUID = 3958344489228625850L;

    private Report report;

    public SavePdfReportCommand(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
