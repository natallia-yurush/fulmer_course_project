package by.ny.client.controller;

import by.ny.server.entity.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class ResultChartController {
    public Button exitLineChart;
    public LineChart<String, Number> lineChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;

    public void exitChart(ActionEvent actionEvent) {
        Stage stage = (Stage) exitLineChart.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        List<Report> reports = ReportsController.getReports();
        XYChart.Series series = new XYChart.Series();
        int num = reports.size();
        for (int i = 0; i < num; i++) {
            series.getData().add(new XYChart.Data(String.valueOf(i), reports.get(i).getResult()));
        }
        series.setName("Показатель банкротсва предприятия");
        lineChart.getData().add(series);
    }
}
