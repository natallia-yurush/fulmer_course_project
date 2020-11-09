package by.ny.client.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import by.ny.client.ConnectionUtil;
import by.ny.server.controller.command.dollarrate.ListDollarRatesCommand;
import by.ny.server.controller.result.dollarrate.ListDollarRateResult;
import by.ny.server.entity.DollarRate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DollarRateTabController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField eurField;

    @FXML
    private TextField bynField;

    @FXML
    private TableView<DollarRate> historyTable;

    @FXML
    private TableColumn<DollarRate, Date> dateColumn;

    @FXML
    private TableColumn<DollarRate, Double> eurColumn;

    @FXML
    private TableColumn<DollarRate, Double> bynColumn;

    @FXML
    private Button addButton;

    @FXML
    void initialize() {
        reloadHistoryTableData();
    }

    public void reloadHistoryTableData() {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new ListDollarRatesCommand());

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ListDollarRateResult result = (ListDollarRateResult) inputStream.readObject();


            ObservableList<DollarRate> dollarRates = FXCollections.observableArrayList();
            dollarRates.addAll(result.getDollarRates());

            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            eurColumn.setCellValueFactory(new PropertyValueFactory<>("EUR"));
            bynColumn.setCellValueFactory(new PropertyValueFactory<>("BYN"));

            historyTable.setItems(dollarRates);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

