package by.ny.server.service;

import by.ny.server.dao.UserDao;
import by.ny.server.entity.User;

import java.util.List;

public class UserService {
    private UserService() {
    }

    private UserDao userDao = UserDao.getInstance();

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public User getUserById(Integer id) {
        return userDao.findUserById(id);
    }

    public boolean deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    public boolean saveUser(User user) {
        return userDao.saveUser(user);
    }

    private static class Holder {
        public static UserService instance = new UserService();
    }

    public static UserService getInstance() {
        return UserService.Holder.instance;
    }
}
