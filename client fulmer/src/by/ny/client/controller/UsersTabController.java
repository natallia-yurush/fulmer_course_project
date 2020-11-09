package by.ny.client.controller;

import by.ny.client.ConnectionUtil;
import by.ny.client.util.ConfirmationDialog;
import by.ny.client.util.InformationDialog;
import by.ny.server.controller.command.user.DeleteUserCommand;
import by.ny.server.controller.command.user.ListUsersCommand;
import by.ny.server.controller.result.user.ListUsersResult;
import by.ny.server.controller.result.user.UpdateUserStatusResult;
import by.ny.server.entity.Company;
import by.ny.server.entity.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    //deleteButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

    @FXML
    void initialize() {
        reloadUsersTableData();
        deleteButton.disableProperty().bind(Bindings.isEmpty(usersTable.getSelectionModel().getSelectedItems()));


        //TODO когда выбирается строка из таблицы -> выводится новая таблица компаний
/*

        tableView.skinProperty().addListener(new ChangeListener<Skin>()
        {
            @Override
            public void changed(ObservableValue<? extends Skin> ov, Skin t, Skin t1)
            {
                if (t1 == null) { return; }

                TableViewSkin tvs = (TableViewSkin)t1;
                ObservableList<Node> kids = tvs.getChildrenUnmodifiable();

                if (kids == null || kids.isEmpty()) { return; }
                flow = (VirtualFlow)kids.get(1);
            }
        });
*/

    }

    public void reloadUsersTableData() {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new ListUsersCommand());

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ListUsersResult result = (ListUsersResult) inputStream.readObject();


            ObservableList<User> users = FXCollections.observableArrayList();
            users.addAll(result.getUsers());

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

        if(ConfirmationDialog.viewMessage("Вы уверены, что хотите удалить пользователя " +
                user.getSurname() + " " + user.getName() + "?").get() != ButtonType.OK) {
            return;
        }

        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new DeleteUserCommand(user.getId()));

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            UpdateUserStatusResult result = (UpdateUserStatusResult) inputStream.readObject();

            if (result.isSuccess()) {
                reloadUsersTableData();
                //TODO reloadCompanyTableData(null);
                InformationDialog.viewMessage("Пользователь успешно удален.");
            } else {
                InformationDialog.viewMessage("Не удалось удалить пользователя.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadCompanyTableData(User user) {
        try {
            Socket socket = ConnectionUtil.getSocket();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new ListUsersCommand());

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ListUsersResult result = (ListUsersResult) inputStream.readObject();


            ObservableList<User> users = FXCollections.observableArrayList();
            users.addAll(result.getUsers());

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
}

