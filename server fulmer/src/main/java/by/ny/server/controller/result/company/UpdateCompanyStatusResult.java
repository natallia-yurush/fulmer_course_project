package by.ny.server.controller.result.company;

import java.io.Serializable;

public class UpdateCompanyStatusResult implements Serializable {
    private static final long serialVersionUID = 1215422174896676438L;

    private boolean success;

    public UpdateCompanyStatusResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
