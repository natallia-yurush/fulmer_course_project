package by.ny.server.controller.result.user;

import by.ny.server.entity.RegistrationStatus;
import by.ny.server.entity.User;

import java.io.Serializable;

public class RegistrationResult implements Serializable {
    private static final long serialVersionUID = 1565946254593546261L;

    private RegistrationStatus registrationStatus;
    private User user;

    public RegistrationResult(RegistrationStatus registrationStatus, User user) {
        this.registrationStatus = registrationStatus;
        this.user = user;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
