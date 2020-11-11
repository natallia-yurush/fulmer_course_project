package by.ny.server.controller.result.dollarrate;

import java.io.Serializable;

public class SaveDollarRateResult implements Serializable {
    private static final long serialVersionUID = 6668860685967999473L;

    private boolean success;

    public SaveDollarRateResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
