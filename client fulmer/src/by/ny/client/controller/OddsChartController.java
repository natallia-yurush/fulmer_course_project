package by.ny.client.controller;

import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OddsChartController {
    public Button exitBarChart;
    public BarChart barChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    private Report report = ReportsController.getSelectedReport();

    public void exitChart(ActionEvent actionEvent) {
        Stage stage = (Stage) exitBarChart.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        if (report == null) {
            InformationDialog.viewMessage("Выберите отчет!");
        } else {
            xAxis.setLabel("Коэффициенты");
            yAxis.setLabel("Значения");

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
            dataSeries1.getData().add(new XYChart.Data<>("x1", report.getX1()));
            dataSeries1.getData().add(new XYChart.Data<>("x2", report.getX2()));
            dataSeries1.getData().add(new XYChart.Data<>("x3", report.getX3()));
            dataSeries1.getData().add(new XYChart.Data<>("x4", report.getX4()));
            dataSeries1.getData().add(new XYChart.Data<>("x5", report.getX5()));
            dataSeries1.getData().add(new XYChart.Data<>("x6", report.getX6()));
            dataSeries1.getData().add(new XYChart.Data<>("x7", report.getX7()));
            dataSeries1.getData().add(new XYChart.Data<>("x8", report.getX8()));
            dataSeries1.getData().add(new XYChart.Data<>("x9", report.getX9()));

            barChart.getData().add(dataSeries1);
            barChart.setTitle("Коэффициенты отчета");
        }


    }
}
