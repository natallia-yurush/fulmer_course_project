package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.CurrentUserUtil;
import by.ny.client.util.ConfirmationDialog;
import by.ny.client.util.InformationDialog;
import by.ny.client.util.Validator;
import by.ny.server.controller.command.company.DeleteCompanyCommand;
import by.ny.server.controller.command.company.ListCompaniesCommand;
import by.ny.server.controller.command.company.SaveCompanyCommand;
import by.ny.server.controller.result.company.ListCompaniesResult;
import by.ny.server.controller.result.company.UpdateCompanyStatusResult;
import by.ny.server.entity.Company;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.VirtualFlow;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyTabController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Company> companyTable;

    @FXML
    private TableColumn<Company, Integer> companyIdColumn;

    @FXML
    private TableColumn<Company, String> companyNameColumn;

    @FXML
    private TableColumn<Company, String> companyAddressColumn;

    @FXML
    private TableColumn<Company, String> companyPhoneNumberColumn;

    @FXML
    private TableColumn<Company, String> companyEmailColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button calculationButton;

    private VirtualFlow flow;

    //private

    private Company selectedCompany;

    @FXML
    void initialize() {
        reloadCompaniesTableData();

        //TODO когда выбирается строка из таблицы -> выводится новая таблица компаний


        companyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Company>() {
            @Override
            public void changed(ObservableValue<? extends Company> observable, Company oldValue, Company newValue) {
                selectedCompany = companyTable.getSelectionModel().getSelectedItem();
                if(selectedCompany != null)
                    changeValues();
            }
        });


    }

    public void reloadCompaniesTableData() {
        deleteButton.disableProperty().bind(Bindings.isEmpty(companyTable.getSelectionModel().getSelectedItems()));

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new ListCompaniesCommand(CurrentUserUtil.getCurrentUser().getId()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ListCompaniesResult result = (ListCompaniesResult) inputStream.readObject();

            //ObservableList<Company>
            ObservableList<Company> companies =
                    FXCollections.observableArrayList();
            companies.addAll(result.getCompanies());
            companyIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            companyAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            companyEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            companyPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            companyTable.setItems(companies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeValues() {
        nameField.setText(selectedCompany.getName());
        addressField.setText(selectedCompany.getAddress());
        phoneNumberField.setText(selectedCompany.getPhoneNumber());
        emailField.setText(selectedCompany.getEmail());
       // deleteButton.setDisable(false);
    }

    public void saveButtonAction() {
        if(!isValid()) {
            return;
        }
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Integer id = null;
            if(selectedCompany != null) {
                id = selectedCompany.getId();
            }

            outputStream.writeObject(new SaveCompanyCommand(new Company(id, nameField.getText(), addressField.getText(),
                    emailField.getText(), phoneNumberField.getText(), CurrentUserUtil.getCurrentUser())));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            UpdateCompanyStatusResult result = (UpdateCompanyStatusResult) inputStream.readObject();

            if(result.isSuccess()) {
                InformationDialog.viewMessage("Компания успешно сохранена!");
                clearButtonAction();
                reloadCompaniesTableData();
            } else {
                InformationDialog.viewMessage("Не удалось сохранить компанию.");
            }


        } catch (Exception e) {
            Logger.getLogger(AuthorizationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void clearButtonAction() {
        nameField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        reloadCompaniesTableData();
    }

    public void deleteButtonAction() {
        if (ConfirmationDialog.viewMessage("Вы уверены, что хотите удалить компанию " +
                selectedCompany.getName() + "?").get() != ButtonType.OK) {
            return;
        }

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new DeleteCompanyCommand(selectedCompany.getId()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            UpdateCompanyStatusResult result = (UpdateCompanyStatusResult) inputStream.readObject();

            if (result.isSuccess()) {
                reloadCompaniesTableData();
                clearButtonAction();
                InformationDialog.viewMessage("Компания успешно удалена!");
            } else {
                InformationDialog.viewMessage("Не удалось удалить компанию.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid() {
        if (nameField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty() ||
                phoneNumberField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
            InformationDialog.viewMessage("Заполните, пожалуйста, все поля!");
            return false;
        }
        if (!Validator.isCorrectEmail(emailField.getText())) {
            InformationDialog.viewMessage("Некорректно введен email.");
            return false;
        } else if (!Validator.isCorrectPhoneNumber(phoneNumberField.getText())) {
            InformationDialog.viewMessage("Некорректно введен номер.");
            return false;
        }
        return true;
    }
}