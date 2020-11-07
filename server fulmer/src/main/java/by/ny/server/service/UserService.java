package by.ny.server.service;

import by.ny.server.dao.UserDao;
import by.ny.server.entity.User;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public boolean deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    public boolean saveUser(User user) {
        return userDao.saveUser(user);
    }
}
