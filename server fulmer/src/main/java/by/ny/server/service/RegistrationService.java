package by.ny.server.service;

import by.ny.server.dao.UserDao;
import by.ny.server.entity.RegistrationStatus;

public class RegistrationService {
    private UserDao userDao;

    public RegistrationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public RegistrationStatus registration(String login, String email) {
        if(userDao.findUserByLogin(login) != null) {
            return RegistrationStatus.EXISTING_LOGIN;
        }
        if(userDao.findUserByEmail(email) != null) {
            return RegistrationStatus.EXISTING_EMAIL;
        }
        return RegistrationStatus.REGISTERED;
    }
}
