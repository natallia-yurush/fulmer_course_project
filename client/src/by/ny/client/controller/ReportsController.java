package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.Company;
import by.ny.server.entity.Report;
import by.ny.server.util.Request;
import by.ny.server.util.RequestType;
import by.ny.server.util.Response;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReportsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Report> reportsTable;

    @FXML
    private TableColumn<Report, Date> dateColumn;

    @FXML
    private TableColumn<Report, Double> x1Column;

    @FXML
    private TableColumn<Report, Double> x2Column;

    @FXML
    private TableColumn<Report, Double> x3Column;

    @FXML
    private TableColumn<Report, Double> x4Column;

    @FXML
    private TableColumn<Report, Double> x5Column;

    @FXML
    private TableColumn<Report, Double> x6Column;

    @FXML
    private TableColumn<Report, Double> x7Column;

    @FXML
    private TableColumn<Report, Double> x8Column;

    @FXML
    private TableColumn<Report, Double> x9Column;

    @FXML
    private TableColumn<Report, Double> resultColumn;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button searchButton;

    @FXML
    private Button resultsChartButton;

    @FXML
    private Button oddsChartButton;

    private static Report selectedReport;
    private static List<Report> reports;


    @FXML
    void initialize() {
        reloadReportsTableData(CompanyTabController.getSelectedCompany());
        oddsChartButton.disableProperty().bind(Bindings.isEmpty(reportsTable.getSelectionModel().getSelectedItems()));


        reportsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        });
    }

    public void reloadReportsTableData(Company company) {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new Request(RequestType.REPORTS_LIST, company.getId()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            reports = (List) ((Response) inputStream.readObject()).getData();

            ObservableList<Report> obsReports = FXCollections.observableArrayList();
            obsReports.addAll(reports);

            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            x1Column.setCellValueFactory(new PropertyValueFactory<>("x1"));
            x2Column.setCellValueFactory(new PropertyValueFactory<>("x2"));
            x3Column.setCellValueFactory(new PropertyValueFactory<>("x3"));
            x4Column.setCellValueFactory(new PropertyValueFactory<>("x4"));
            x5Column.setCellValueFactory(new PropertyValueFactory<>("x5"));
            x6Column.setCellValueFactory(new PropertyValueFactory<>("x6"));
            x7Column.setCellValueFactory(new PropertyValueFactory<>("x7"));
            x8Column.setCellValueFactory(new PropertyValueFactory<>("x8"));
            x9Column.setCellValueFactory(new PropertyValueFactory<>("x9"));
            resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

            reportsTable.setItems(obsReports);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchButtonAction() {
        LocalDate date = calendar.getValue();
        if (date == null) {
            InformationDialog.viewMessage("Выберите дату!");
            return;
        }
        reportsTable.setItems(null);
        ObservableList<Report> obsReports = FXCollections.observableArrayList();
        for (Report report : reports) {
            LocalDate reportDate = Instant.ofEpochMilli(report.getDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (date.equals(reportDate)) {
                obsReports.add(report);
            }
        }
        reportsTable.setItems(obsReports);
    }

    public void resultsChartButtonAction() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/ResultsChartWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Диаграмма");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void oddsChartButtonAction() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/by/ny/client/view/OddsChartWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Диаграмма");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void clearButtonAction() {
        reloadReportsTableData(CompanyTabController.getSelectedCompany());
    }

    public static Report getSelectedReport() {
        return selectedReport;
    }

    public static List<Report> getReports() {
        return reports;
    }

}
