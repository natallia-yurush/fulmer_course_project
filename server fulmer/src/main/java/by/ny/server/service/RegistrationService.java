package by.ny.server.service;

import by.ny.server.dao.UserDao;
import by.ny.server.entity.RegistrationStatus;

public class RegistrationService {
    private UserDao userDao = UserDao.getInstance();

    public RegistrationStatus registration(String login, String email) {
        if(userDao.findUserByEmail(email) != null) {
            return RegistrationStatus.EXISTING_EMAIL;
        }
        if(userDao.findUserByLogin(login) != null) {
            return RegistrationStatus.EXISTING_LOGIN;
        }
        return RegistrationStatus.REGISTERED;
    }
}
