package by.ny.server.controller.command.company;

import java.io.Serializable;

public class ListCompaniesCommand implements Serializable {
    private static final long serialVersionUID = -6035661333386314231L;

    private Integer userId;

    public ListCompaniesCommand(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
