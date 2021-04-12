package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.DollarRate;
import by.ny.server.util.Request;
import by.ny.server.util.RequestType;
import by.ny.server.util.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.List;
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
            outputStream.writeObject(new Request(RequestType.DOLLAR_RATES_LIST, null));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            List<DollarRate> resultDollarRate = (List) ((Response) inputStream.readObject()).getData();

            ObservableList<DollarRate> dollarRates = FXCollections.observableArrayList();
            dollarRates.addAll(resultDollarRate);

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
            outputStream.writeObject(new Request(RequestType.SAVE_DOLLAR_RATE,
                    new DollarRate(null, new Date(), eurSpinner.getValue(), bynSpinner.getValue())));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Boolean result = (Boolean) ((Response) inputStream.readObject()).getData();

            if (result) {
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
        SpinnerValueFactory<Double> factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, 10, 0.84, 0.01);
        eurSpinner.setValueFactory(factory);
        eurSpinner.setEditable(true);

        factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, 20, 2.57, 0.01);
        bynSpinner.setValueFactory(factory);
        bynSpinner.setEditable(true);

        StringConverter<Double> scd = eurSpinner.getValueFactory().getConverter();
        StringConverter<Double> scd2 = new StringConverter<>() {
            @Override
            public Double fromString(String value) {
                try {
                    return scd.fromString(value);
                } catch (RuntimeException re) {
                    InformationDialog.viewMessage("Неправильный формат данных!");
                    return 0.01;
                }
            }

            @Override
            public String toString(Double value) {
                return scd.toString(value);
            }
        };
        eurSpinner.getValueFactory().setConverter(scd2);
        bynSpinner.getValueFactory().setConverter(scd2);
    }
}

