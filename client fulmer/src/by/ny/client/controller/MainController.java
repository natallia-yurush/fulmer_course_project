package by.ny.client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.ny.client.ConnectionUtil;
import by.ny.client.CurrentUserUtil;
import by.ny.client.util.Message;
import by.ny.server.controller.command.user.AuthorizationCommand;
import by.ny.server.controller.result.user.AuthorizationResult;
import by.ny.server.entity.UserRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

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
        /*assert loginField != null : "fx:id=\"loginField\" was not injected: check your FXML file 'Main.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Main.fxml'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'Main.fxml'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'Main.fxml'.";*/

    }


    public void registration(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/Registration.fxml"));
            stage.setTitle("Регистрация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            //stage.initModality(Modality.WINDOW_MODAL);
            //stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            //client.Client.getInstance().send("registration");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorization(ActionEvent actionEvent) {

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new AuthorizationCommand(loginField.getText(), passwordField.getText()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            AuthorizationResult result = (AuthorizationResult) inputStream.readObject();

            if (result.isAuthorized()) {
                CurrentUserUtil.setCurrentUser(result.getUser());
                if (result.getUser().getRole() == UserRole.ADMIN) {
                    //TODO
                    //new AdministratorWindow().setVisible(true);
                } else if (result.getUser().getRole() == UserRole.CLIENT) {
                    //TODO
                    //new ClientWindow().setVisible(true);
                }
            } else {
                Message.viewMessage("Авторизация не выполнена!");
            }
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

