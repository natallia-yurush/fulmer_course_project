package by.ny.server.controller.command.user;

import by.ny.server.entity.User;

import java.io.Serializable;

public class SaveUserCommand implements Serializable {
    private static final long serialVersionUID = 8919876977752405386L;

    private User user;

    public SaveUserCommand(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
