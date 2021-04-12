package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.dialog.ConfirmationDialog;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.Company;
import by.ny.server.entity.User;
import by.ny.server.util.Request;
import by.ny.server.util.RequestType;
import by.ny.server.util.Response;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.VirtualFlow;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class UsersTabController {

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> userSurnameColumn;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> userPhoneNumberColumn;

    @FXML
    private TableColumn<User, String> userEmailColumn;

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
    private Button deleteButton;

    private VirtualFlow flow;

    private ObservableList<User> users =
            FXCollections.observableArrayList();

    @FXML
    void initialize() {
        reloadUsersTableData();
        deleteButton.disableProperty().bind(Bindings.isEmpty(usersTable.getSelectionModel().getSelectedItems()));


        //TODO когда выбирается строка из таблицы -> выводится новая таблица компаний

        usersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                User user = usersTable.getSelectionModel().getSelectedItem();
                reloadCompanyTableData(user);
            }
        });


    }

    public void reloadUsersTableData() {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new Request(RequestType.USERS_LIST, null));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            List<User> resultUsers = (List) ((Response) inputStream.readObject()).getData();
            //ListUsersResult result = (ListUsersResult) inputStream.readObject();


            //ObservableList<User>
            users.addAll(resultUsers);

            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            userSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            userPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            usersTable.setItems(users);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        User user = usersTable.getSelectionModel().getSelectedItem();

        if (ConfirmationDialog.viewMessage("Вы уверены, что хотите удалить пользователя " +
                user.getSurname() + " " + user.getName() + "?").get() != ButtonType.OK) {
            return;
        }

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new Request(RequestType.DELETE_USER, user.getId()));
            //outputStream.writeObject(new DeleteUserCommand(user.getId()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Boolean result = (Boolean) ((Response) inputStream.readObject()).getData();
           // UpdateUserStatusResult result = (UpdateUserStatusResult) inputStream.readObject();

            if (result) {
                reloadUsersTableData();
                reloadCompanyTableData(null);
                InformationDialog.viewMessage("Пользователь успешно удален.");
            } else {
                InformationDialog.viewMessage("Не удалось удалить пользователя.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadCompanyTableData(User user) {
        if (user == null) {
            clearCompanyTable();
            return;
        }
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new Request(RequestType.COMPANIES_LIST, user.getId()));
            //outputStream.writeObject(new ListCompaniesCommand(user.getId()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            List<Company> resultCompanies = (List) ((Response) inputStream.readObject()).getData();
            //ListCompaniesResult result = (ListCompaniesResult) inputStream.readObject();


            ObservableList<Company> companies = FXCollections.observableArrayList();
            companies.addAll(resultCompanies);

            companyIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            companyAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            companyPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            companyEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            companyTable.setItems(companies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCompanyTable() {
        companyTable.getItems().clear();
    }

}

