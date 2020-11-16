package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.server.controller.command.StatisticCommand;
import by.ny.server.controller.result.StatisticResult;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticController {

    @FXML
    private TextField numberOfUsersField;

    @FXML
    private TextField numberOfReportsField;

    @FXML
    private TextField numberOfCompaniesField;

    public StatisticController() {
    }

    @FXML
    void initialize() {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new StatisticCommand());

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            StatisticResult result = (StatisticResult) inputStream.readObject();

            numberOfUsersField.setText(result.getNumberOfUsers().toString());
            numberOfCompaniesField.setText(result.getNumberOfCompanies().toString());
            numberOfReportsField.setText(result.getNumberOfReports().toString());

        } catch (Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


}


