package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.util.InformationDialog;
import by.ny.server.controller.command.dollarrate.ListDollarRatesCommand;
import by.ny.server.controller.command.dollarrate.SaveDollarRateCommand;
import by.ny.server.controller.result.dollarrate.ListDollarRateResult;
import by.ny.server.controller.result.dollarrate.SaveDollarRateResult;
import by.ny.server.entity.DollarRate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DollarRateTabController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Double> eurSpinner;

    @FXML
    private Spinner<Double> bynSpinner;

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
        configureSpinners();
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

    public void addDollarRate() {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new SaveDollarRateCommand(
                    new DollarRate(null, new Date(), eurSpinner.getValue(), bynSpinner.getValue())));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            SaveDollarRateResult result = (SaveDollarRateResult) inputStream.readObject();

            if(result.isSuccess()) {
                InformationDialog.viewMessage("Курс успешно добавлен!");
                reloadHistoryTableData();
            } else {
                InformationDialog.viewMessage("Не удалось добавить новый курс.");
            }

        } catch (Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void configureSpinners() {
        //configure the spinners
        SpinnerValueFactory<Double> factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 0.84, 0.01);
        eurSpinner.setValueFactory(factory);
        eurSpinner.setEditable(true);

        factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 20, 2.57, 0.01);
        bynSpinner.setValueFactory(factory);
        bynSpinner.setEditable(true);
    }
}

