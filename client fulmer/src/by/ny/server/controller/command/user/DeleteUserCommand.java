package by.ny.server.controller.command.user;

import java.io.Serializable;

public class DeleteUserCommand implements Serializable{
    private static final long serialVersionUID = -2971651893320495227L;

    private Integer userId;

    public DeleteUserCommand(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
