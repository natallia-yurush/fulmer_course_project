package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.controller.command.report.SaveReportCommand;
import by.ny.server.controller.result.report.SaveReportResult;
import by.ny.server.entity.Currency;
import by.ny.server.entity.Report;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
/*
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {

                // получаем выбранный элемент RadioButton
                RadioButton selectedBtn = (RadioButton) newValue;
                selectedLbl.setText("Selected: " + selectedBtn.getText());

        });*/
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                // Has selection.
                if (toggleGroup.getSelectedToggle() != null) {
                    RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
                    selectedCurrency = button.getText();
                    //System.out.println("Button: " + button.getText());
                    //labelInfo.setText("You are " + button.getText());
                }
            }
        });

    }

    private void configureSpinners() {
       // SpinnerValueFactory<Double> factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.01);
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
    }

    public void calculationButtonAction() {
        if(x1Spinner.getValue() == 0 || x2Spinner.getValue() == 0 || x3Spinner.getValue() == 0 || x4Spinner.getValue() == 0 ||
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
            outputStream.writeObject(new SaveReportCommand(report));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            SaveReportResult result = (SaveReportResult) inputStream.readObject();
            reportResult = result.getReport();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/CalculationResultWindow.fxml"));
            stage.setTitle("Результат");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();


        } catch(Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Report getResult() {
        return reportResult;
    }
}
