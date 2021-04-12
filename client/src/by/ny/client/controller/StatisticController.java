package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.server.util.Request;
import by.ny.server.util.RequestType;
import by.ny.server.util.Response;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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
            outputStream.writeObject(new Request(RequestType.STATISTIC, null));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            List<Object> objects = (List) ((Response) inputStream.readObject()).getData();

            numberOfUsersField.setText(((Integer)objects.get(2)).toString());
            numberOfCompaniesField.setText(((Integer)objects.get(0)).toString());
            numberOfReportsField.setText(((Integer)objects.get(1)).toString());

        } catch (Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


}


