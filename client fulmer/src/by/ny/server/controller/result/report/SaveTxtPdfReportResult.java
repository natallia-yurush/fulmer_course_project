package by.ny.server.controller.result.report;

import java.io.Serializable;

public class SaveTxtPdfReportResult implements Serializable {
    private static final long serialVersionUID = 1059335485430959866L;

    private boolean success;

    public SaveTxtPdfReportResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
