package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.CurrentUserUtil;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.User;
import by.ny.server.entity.UserRole;
import by.ny.server.util.Request;
import by.ny.server.util.RequestType;
import by.ny.server.util.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
    }


    public void registration(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/RegistrationWindow.fxml"));
            stage.setTitle("Регистрация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorization(ActionEvent actionEvent) {

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new Request(RequestType.AUTHORIZATION, Arrays.asList(loginField.getText(), passwordField.getText())));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            User user = (User) ((Response) inputStream.readObject()).getData();

            if (user != null) {
                CurrentUserUtil.setCurrentUser(user);
                if (user.getRole() == UserRole.ADMIN) {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/AdminWindow.fxml"));
                    stage.setTitle("Меню администратора");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                } else if (user.getRole() == UserRole.CLIENT) {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/UserWindow.fxml"));
                    stage.setTitle("Меню пользователя");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } else {
                InformationDialog.viewMessage("Авторизация не выполнена!");
            }
        } catch (Exception ex) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

