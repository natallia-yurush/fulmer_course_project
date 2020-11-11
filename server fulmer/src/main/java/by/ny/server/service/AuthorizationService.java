package by.ny.server.service;

import by.ny.server.dao.UserDao;
import by.ny.server.entity.User;

public class AuthorizationService {
    private UserDao userDao = UserDao.getInstance();

    public User authenticate(String login, String password) {
        User user = userDao.findUserByLogin(login);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
