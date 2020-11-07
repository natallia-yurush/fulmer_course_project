package by.ny.server.controller.result.user;

import by.ny.server.entity.User;

import java.io.Serializable;

public class AuthorizationResult implements Serializable {
    private static final long serialVersionUID = 5890430905552289599L;

    private boolean authorized;
    private User user;

    public AuthorizationResult(boolean authorized, User user) {
        this.authorized = authorized;
        this.user = user;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
