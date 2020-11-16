package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.CurrentUserUtil;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.controller.command.user.RegistrationCommand;
import by.ny.server.controller.result.user.RegistrationResult;
import by.ny.server.entity.RegistrationStatus;
import by.ny.server.entity.UserRole;
import by.ny.client.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    public void registration(ActionEvent actionEvent) {
        if(!isValid()) {
            return;
        }

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            UserRole role = UserRole.CLIENT;

            outputStream.writeObject(new RegistrationCommand(surnameField.getText(), nameField.getText(),
                    loginField.getText(), passwordField.getText(), phoneNumberField.getText(), role, emailField.getText()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            RegistrationResult result = (RegistrationResult) inputStream.readObject();

            if (result.getRegistrationStatus() == RegistrationStatus.EXISTING_EMAIL) {
                InformationDialog.viewMessage("Пользователь с данной почтой уже существует!");
            } else if (result.getRegistrationStatus() == RegistrationStatus.EXISTING_LOGIN) {
                InformationDialog.viewMessage("Пользователь с данным логином уже существует!");
            } else if (result.getRegistrationStatus() == RegistrationStatus.REGISTERED) {
                CurrentUserUtil.setCurrentUser(result.getUser());
                InformationDialog.viewMessage("Пользователь успешно зарегистрирован!");

                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }

        } catch (Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private boolean isValid() {
        if (nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty() ||
                loginField.getText().trim().isEmpty() ||
                passwordField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() || phoneNumberField.getText().trim().isEmpty()) {
            InformationDialog.viewMessage("Заполните, пожалуйста, все поля!");
            return false;
        }
        if (!Validator.isCorrectEmail(emailField.getText())) {
            InformationDialog.viewMessage("Некорректно введен email.");
            return false;
        } else if (!Validator.isCorrectLogin(loginField.getText())) {
            InformationDialog.viewMessage("Логин слишком длинный! Придумайте новый.");
            return false;
        } else if (!Validator.isCorrectPassword(passwordField.getText())) {
            InformationDialog.viewMessage("Неправильный тип пароля! Должно быть не менее 8 символов, " +
                    "не менее 1 заглавной буквы, 1 строчной буквы и 1 цифры");
            return false;
        } else if (!Validator.isCorrectPhoneNumber(phoneNumberField.getText())) {
            InformationDialog.viewMessage("Некорректно введен номер.");
            return false;
        }
        return true;
    }

}

