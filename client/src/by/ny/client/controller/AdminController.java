package by.ny.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    public void privateOfficeAction() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/PrivateOfficeWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Личный кабинет");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
