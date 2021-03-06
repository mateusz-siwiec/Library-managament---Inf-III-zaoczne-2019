package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.entities.User;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPaneController implements Initializable {

    /**
     * Fields controller class
     */
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnLogout;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfPesel;

    @FXML
    private TextField addtfName;
    @FXML
    private TextField addtfLogin;
    @FXML
    private TextField addtfSurname;
    @FXML
    private TextField addtfPhoneNumber;
    @FXML
    private TextField addtfAge;
    @FXML
    private TextField addtfPassword;
    @FXML
    private TextField addtfPesel;
    @FXML
    private TextField addtfName1;
    @FXML
    private TextField addtfLogin1;
    @FXML
    private TextField addtfSurname1;
    @FXML
    private TextField addtfPhoneNumber1;
    @FXML
    private TextField addtfAge1;
    @FXML
    private TextField addtfPassword1;
    @FXML
    private TextField addtfPesel1;
    @FXML
    private ComboBox<String> roleCombobox;
    @FXML
    private ComboBox<String> editRoleCombobox;

    @FXML
    private TableView<User> editUserTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> surnameColumn;
    @FXML
    private TableColumn<User, String> peselColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> ageColumn;
    @FXML
    private TableColumn<User, String> phoneNumberColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private Label startLabel;

    private ScreenController screen;

    private Stage stage;

    private ObservableList<User> ObservableListUsers;

    private int userId;
    private Actions actions = new Actions();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actions.initDatabase();
        userId = LoginController.id;
        loadUsersDataFromDatabaseIntoTable(null);
        roleCombobox.getItems().addAll("ADMIN", "LIBRARIAN", "READER");
        editRoleCombobox.getItems().addAll("ADMIN", "LIBRARIAN", "READER");

    }

    /**
     * Getting logged user data
     * @param user
     */
    public void takeLoggedUserData(User user) {
        String phoneNumber = String.valueOf(user.getPhoneNumber());
        String pesel = String.valueOf(user.getPesel());
        userId = user.getId();

        tfUsername.setText(user.getLogin());
        tfName.setText(user.getName());
        tfSurname.setText(user.getSurname());
        tfPhoneNumber.setText(phoneNumber);
        tfAge.setText(String.valueOf(user.getAge()));
        tfPassword.setText(user.getPassword());
        tfPesel.setText(pesel);

        startLabel.setText("Hello " + user.getName() + " " + user.getSurname());
    }

    /**
     * Load users data from database into table
     * @param event
     */
    @FXML
    private void loadUsersDataFromDatabaseIntoTable(ActionEvent event) {
        Transaction transaction = actions.session.beginTransaction();
        List allUsers = actions.session.createCriteria(User.class).list();
        transaction.commit();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        editUserTable.setItems(null);
        editUserTable.setItems(ObservableListUsers);

        ObservableList usersList = FXCollections.observableArrayList(allUsers);
        editUserTable.setItems(usersList);
    }

    /**
     * adding users
     * @param event
     */
    @FXML
    private void addUser(ActionEvent event) {

        String login = addtfLogin.getText();
        String name = addtfName.getText();
        String surname = addtfSurname.getText();
        int age = Integer.parseInt(addtfAge.getText());
        int phoneNumber = Integer.parseInt(addtfPhoneNumber.getText());
        String password = actions.get_SHA_512_SecurePassword(addtfPassword.getText(),"securePassword");
        long pesel = Integer.parseInt(addtfPesel.getText());
        String role = roleCombobox.getSelectionModel().getSelectedItem();

        User user = new User(login, password, name, surname, age, pesel, phoneNumber, role);

        actions.saveToDatabase(user);

        clearAddUserFields();
        refreshEditUsersTable();
    }

    /**
     * Clearing user fields after adding user
     */
    private void clearAddUserFields() {
        addtfLogin.clear();
        addtfName.clear();
        addtfSurname.clear();
        addtfPassword.clear();
        addtfPesel.clear();
        addtfPhoneNumber.clear();
        roleCombobox.getSelectionModel().clearSelection();
    }

    /**
     * Refreshing user table
     */
    @FXML
    private void refreshEditUsersTable() {

        Transaction transaction = actions.session.beginTransaction();
        List allUsers = actions.session.createCriteria(User.class).list();
        transaction.commit();

        ObservableList usersList = FXCollections.observableArrayList(allUsers);
        editUserTable.setItems(usersList);
        editUserTable.refresh();
    }

    /**
     * Edit user
     * @param event
     */
    @FXML
    private void editUser(ActionEvent event) {

        String login = addtfLogin1.getText();
        String name = addtfName1.getText();
        String surname = addtfSurname1.getText();
        int age = Integer.parseInt(addtfAge1.getText());
        int phoneNumber = Integer.parseInt(addtfPhoneNumber1.getText());
        String password = actions.get_SHA_512_SecurePassword(addtfPassword1.getText(),"securePassword");
        long pesel = Integer.parseInt(addtfPesel1.getText());
        int id = editUserTable.getSelectionModel().getSelectedItem().getId();
        String role = editRoleCombobox.getSelectionModel().getSelectedItem();

        User userEdit = new User(id, login, password, name, surname, age, pesel, phoneNumber, role);

        actions.updateObject(userEdit);

        clearEditUserFields();

        refreshEditUsersTable();
    }

    /**
     * Clear user edit fields
     */
    private void clearEditUserFields() {
        addtfLogin1.clear();
        addtfName1.clear();
        addtfSurname1.clear();
        addtfPassword1.clear();
        addtfPesel1.clear();
        addtfPhoneNumber1.clear();
        roleCombobox.getSelectionModel().clearSelection();
    }

    /**
     * Handle edit user table
     * @param event
     */
    @FXML
    private void handleEditUserTable(MouseEvent event) {
        User selected = editUserTable.getSelectionModel().getSelectedItem();

        if (selected != null) {

            String phone = String.valueOf(selected.getPhoneNumber());
            String pesel = String.valueOf(selected.getPesel());

            addtfName1.setText(selected.getName());
            addtfSurname1.setText(selected.getSurname());
            addtfAge1.setText(String.valueOf(selected.getAge()));
            addtfPhoneNumber1.setText(phone);
            addtfPassword1.setText(selected.getPassword());
            addtfLogin1.setText(selected.getLogin());
            addtfPesel1.setText(pesel);
        }
    }

    /**
     * Delete user
     * @param event
     */
    @FXML
    private void deleteUser(ActionEvent event) {

        User user = editUserTable.getSelectionModel().getSelectedItem();

        actions.deleteFromDatabase(user);

        clearEditUserFields();
        refreshEditUsersTable();
    }

    /**
     * Edit admin
     * @param event
     */
    @FXML
    private void editAdmin(ActionEvent event) {

        if (tfName.isDisabled()) {
            tfName.setDisable(false);
            tfSurname.setDisable(false);
            tfAge.setDisable(false);
            tfPhoneNumber.setDisable(false);
            tfPassword.setDisable(false);
            tfUsername.setDisable(false);
            tfPesel.setDisable(false);
        } else {

            String username = tfUsername.getText();
            String firstname = tfName.getText();
            String surname = tfSurname.getText();
            int age = Integer.parseInt(tfAge.getText());
            int phone = Integer.parseInt(tfPhoneNumber.getText());
            String password = actions.get_SHA_512_SecurePassword(tfPassword.getText(), "securePassword");
            long pesel = Integer.parseInt(tfPesel.getText());


            User userEdit = new User(userId, username, password, firstname, surname, age, pesel, phone, "ADMIN");

            actions.updateObject(userEdit);

            tfName.setDisable(true);
            tfSurname.setDisable(true);
            tfAge.setDisable(true);
            tfPhoneNumber.setDisable(true);
            tfPassword.setDisable(true);
            tfUsername.setDisable(true);
            tfPesel.setDisable(true);
        }
    }

    /**
     * Logout from application
     * @param event
     * @throws IOException
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stage = (Stage) anchorPane.getScene().getWindow();
        screen = new ScreenController(btnLogout.getScene());

        screen.addScreen("Login", FXMLLoader.load(getClass().getResource("/views/Login.fxml")));
        screen.activate("Login", stage);
    }
}
