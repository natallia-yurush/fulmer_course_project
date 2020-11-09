package by.ny.server.controller.result.user;

import by.ny.server.entity.User;

import java.io.Serializable;
import java.util.List;

public class ListUsersResult implements Serializable {
    private static final long serialVersionUID = 4598727763434050923L;

    private List<User> users;

    public ListUsersResult(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
