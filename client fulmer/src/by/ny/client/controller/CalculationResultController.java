package by.ny.client.controller;

import by.ny.client.dialog.InformationDialog;
import by.ny.client.thread.SavePdfThread;
import by.ny.client.thread.SaveTxtThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculationResultController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField numericResultField;

    @FXML
    private TextField textResultField;

    @FXML
    private Button txtSaveButton;

    @FXML
    private Button pdfSaveButton;

    @FXML
    void initialize() {
        numericResultField.setText(CalculationController.getResult().getResult().toString());
        if(CalculationController.getResult().getResult() > 0) {
            textResultField.setText("Предприятие является платежеспособным.");
        } else {
            textResultField.setText("Вероятное банкротство предприятия.");
        }
    }

    public void saveTxtButtonAction() {
        Thread thread = new Thread(new SaveTxtThread());
        thread.start();
        InformationDialog.viewMessage("Отчет успешно сохранен!");
    }

    public void savePdfButtonAction() {
        Thread thread = new Thread(new SavePdfThread());
        thread.start();
        InformationDialog.viewMessage("Отчет успешно сохранен!");
    }

}
