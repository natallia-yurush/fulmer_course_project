package by.ny.server.controller.result.user;

import java.io.Serializable;

public class UpdateUserStatusResult implements Serializable {
    private static final long serialVersionUID = -4735925495858277759L;

    private boolean success;

    public UpdateUserStatusResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
