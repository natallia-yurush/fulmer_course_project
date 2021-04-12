package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.Currency;
import by.ny.server.entity.Report;
import by.ny.server.util.Request;
import by.ny.server.util.RequestType;
import by.ny.server.util.Response;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton usdRadioButton;

    @FXML
    private RadioButton eurRadioButton;

    @FXML
    private RadioButton bynRadioButton;

    @FXML
    private Spinner<Double> x1Spinner;

    @FXML
    private Spinner<Double> x2Spinner;

    @FXML
    private Spinner<Double> x3Spinner;

    @FXML
    private Spinner<Double> x4Spinner;

    @FXML
    private Spinner<Double> x5Spinner;

    @FXML
    private Spinner<Double> x6Spinner;

    @FXML
    private Spinner<Double> x7Spinner;

    @FXML
    private Spinner<Double> x8Spinner;

    @FXML
    private Spinner<Double> x9Spinner;

    @FXML
    private Button calculationButton;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private String selectedCurrency = "USD";
    private static Report reportResult;

    @FXML
    void initialize() {
        configureSpinners();


        usdRadioButton.setToggleGroup(toggleGroup);
        eurRadioButton.setToggleGroup(toggleGroup);
        bynRadioButton.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
                selectedCurrency = button.getText();
            }
        });

    }

    private void configureSpinners() {
        x1Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x1Spinner.setEditable(true);
        x2Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x2Spinner.setEditable(true);
        x3Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x3Spinner.setEditable(true);
        x4Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x4Spinner.setEditable(true);
        x5Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x5Spinner.setEditable(true);
        x6Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x6Spinner.setEditable(true);
        x7Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x7Spinner.setEditable(true);
        x8Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x8Spinner.setEditable(true);
        x9Spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01));
        x9Spinner.setEditable(true);

        StringConverter<Double> scd = x1Spinner.getValueFactory().getConverter();
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

        x1Spinner.getValueFactory().setConverter(scd2);
        x2Spinner.getValueFactory().setConverter(scd2);
        x3Spinner.getValueFactory().setConverter(scd2);
        x4Spinner.getValueFactory().setConverter(scd2);
        x5Spinner.getValueFactory().setConverter(scd2);
        x6Spinner.getValueFactory().setConverter(scd2);
        x7Spinner.getValueFactory().setConverter(scd2);
        x8Spinner.getValueFactory().setConverter(scd2);
        x9Spinner.getValueFactory().setConverter(scd2);
    }

    public void calculationButtonAction() {
        if (x1Spinner.getValue() == 0 || x2Spinner.getValue() == 0 || x3Spinner.getValue() == 0 || x4Spinner.getValue() == 0 ||
                x5Spinner.getValue() == 0 || x6Spinner.getValue() == 0 || x7Spinner.getValue() == 0 || x8Spinner.getValue() == 0 ||
                x9Spinner.getValue() == 0) {
            InformationDialog.viewMessage("Заполните, пожалуйста, все поля!");
            return;
        }
        try {
            Socket socket = ConnectionUtil.getSocket();

            Currency currency = Currency.valueOf(selectedCurrency);
            Report report = new Report(null, CompanyTabController.getSelectedCompany(), null, null,
                    x1Spinner.getValue(), x2Spinner.getValue(), x3Spinner.getValue(), x4Spinner.getValue(), x5Spinner.getValue(),
                    x6Spinner.getValue(), x7Spinner.getValue(), x8Spinner.getValue(), x9Spinner.getValue(), null, currency);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new Request(RequestType.SAVE_REPORT, report));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            reportResult = (Report) ((Response) inputStream.readObject()).getData();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/CalculationResultWindow.fxml"));
            stage.setTitle("Результат");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Report getResult() {
        return reportResult;
    }
}
