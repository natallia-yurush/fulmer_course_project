package by.ny.server.service;

import by.ny.server.dao.UserDao;
import by.ny.server.entity.User;

import java.util.List;

public class UserService {
    private UserService() {
    }

    private UserDao userDao = new UserDao();

    public List<User> listUsers() {
        return userDao.findAll();
    }

    public User getUserById(Integer id) {
        return userDao.findById(id);
    }

    public boolean deleteUser(Integer id) {
        return userDao.delete(id);
    }

    public boolean saveUser(User user) {
        return userDao.save(user);
    }

    private static class Holder {
        public static UserService instance = new UserService();
    }

    public static UserService getInstance() {
        return Holder.instance;
    }
}
