package by.ny.server.controller.command.user;

import by.ny.server.entity.UserRole;

import java.io.Serializable;

public class RegistrationCommand implements Serializable {
    private static final long serialVersionUID = -6180527100499531617L;

    private String surname;
    private String name;
    private String login;
    private String password;
    private String phoneNumber;
    private UserRole role;
    private String email;

    public RegistrationCommand(String surname, String name, String login, String password, String phoneNumber, UserRole role, String email) {
        this.surname = surname;
        this.name = name;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
