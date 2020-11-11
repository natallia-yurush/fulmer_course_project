package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.CurrentUserUtil;
import by.ny.client.util.InformationDialog;
import by.ny.client.util.PasswordDialog;
import by.ny.client.util.Validator;
import by.ny.server.controller.command.user.SaveUserCommand;
import by.ny.server.controller.result.user.UpdateUserStatusResult;
import by.ny.server.entity.RegistrationStatus;
import by.ny.server.entity.User;
import by.ny.server.entity.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrivateOfficeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private Button saveButton;

    @FXML
    private PasswordField passwordField;

    private String oldLogin;
    private String oldEmail;

    @FXML
    void initialize() {
        reloadUsersData();
        oldEmail = emailField.getText();
        oldLogin = loginField.getText();
    }

    public void reloadUsersData() {
        nameField.setText(CurrentUserUtil.getCurrentUser().getName());
        surnameField.setText(CurrentUserUtil.getCurrentUser().getSurname());
        loginField.setText(CurrentUserUtil.getCurrentUser().getLogin());
        passwordField.setText(CurrentUserUtil.getCurrentUser().getPassword());
        phoneNumberField.setText(CurrentUserUtil.getCurrentUser().getPhoneNumber());
        emailField.setText(CurrentUserUtil.getCurrentUser().getEmail());
    }

    public void saveUsersData() {
        PasswordDialog pd = new PasswordDialog();
        Optional<String> optionalS = pd.showAndWait();
        optionalS.ifPresent(password -> {
            if(password.equals(CurrentUserUtil.getCurrentUser().getPassword())) {
                if (isValid()) {
                    try {
                        Socket socket = ConnectionUtil.getSocket();

                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        UserRole role = UserRole.CLIENT;

                        User user = new User(CurrentUserUtil.getCurrentUser().getId(), surnameField.getText(), nameField.getText(),
                                loginField.getText(), passwordField.getText(), phoneNumberField.getText(), role, emailField.getText());
                        outputStream.writeObject(new SaveUserCommand(user));

                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        RegistrationStatus status = (RegistrationStatus) inputStream.readObject();
                        UpdateUserStatusResult updateResult = (UpdateUserStatusResult) inputStream.readObject();


                        if (!oldEmail.equals(emailField.getText())
                                && status == RegistrationStatus.EXISTING_EMAIL) {
                            InformationDialog.viewMessage("Пользователь с данной почтой уже существует!");
                        } else if (!oldLogin.equals(loginField.getText()) &&
                                status == RegistrationStatus.EXISTING_LOGIN) {
                            InformationDialog.viewMessage("Пользователь с данным логином уже существует!");
                        } else if (updateResult.isSuccess()) {
                            CurrentUserUtil.setCurrentUser(user);
                            InformationDialog.viewMessage("Данные успешно изменены!");
                        } else {
                            InformationDialog.viewMessage("Не удалось изменить данные.");
                        }
                    } catch (Exception e) {
                        Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            } else {
                InformationDialog.viewMessage("Неверно введен пароль.");
            }
        });
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
