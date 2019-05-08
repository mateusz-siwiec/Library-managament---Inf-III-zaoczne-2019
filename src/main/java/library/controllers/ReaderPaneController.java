package library.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.entities.Book;
import library.entities.Orders;
import library.entities.User;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReaderPaneController implements Initializable {


    @FXML
    private ComboBox<Book> comboBookAvailbility;
    @FXML
    private TableColumn<Orders, String> columnFrom;
    @FXML
    private TableColumn<Orders, String> columnTo;
    @FXML
    private TableColumn<Orders, String> columnBookTitle;
    @FXML
    private TableColumn<Orders, String> columnBookAuthor;
    @FXML
    private TableView<Orders> userOrderTable;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfName;
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
    private Label availbility;
    @FXML
    private Button btnLogout;
    @FXML
    private AnchorPane anchorPane;

    private ScreenController screen;

    private Stage stage;

    private Actions actions = new Actions();

    private int userId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId = LoginController.id;
        actions.initDatabase();
        loadUserOrdersIntoTable();
        refreshBooksComboBox();
    }

    public void takeLoggedUserData(User user) {
        userId = user.getId();
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
    }

    @FXML
    private void selfEdit(ActionEvent event) {

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
            String password = tfPassword.getText();
            long pesel = Integer.parseInt(tfPesel.getText());


            User userEdit = new User(userId, username, password, firstname, surname, age, pesel, phone, "READER");

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

    @FXML
    private void loadUserOrdersIntoTable() {
        Transaction transaction = actions.session.beginTransaction();
        List<Orders> allOrders = actions.session.createCriteria(Orders.class).list();

        List<Orders> userOrders = allOrders.stream()
                .filter(orders -> orders.getUser().getId() == userId)
                .collect(Collectors.toList());
        transaction.commit();

        columnFrom.setCellValueFactory(param -> {
            if (param.getValue().getUser().getId() == userId) {
                String readerLogin = param.getValue().getDateFrom();
                return new SimpleStringProperty(readerLogin);
            } else {
                return null;
            }
        });

        columnTo.setCellValueFactory(param -> {
            if (param.getValue().getUser().getId() == userId) {
                String readerLogin = param.getValue().getDateTo();
                return new SimpleStringProperty(readerLogin);
            } else {
                return null;
            }
        });

        columnBookAuthor.setCellValueFactory(param -> {
            if (param.getValue().getUser().getId() == userId) {
                String readerLogin = param.getValue().getBook().getAuthor();
                return new SimpleStringProperty(readerLogin);
            } else {
                return null;
            }
        });

        columnBookTitle.setCellValueFactory(param -> {
            if (param.getValue().getUser().getId() == userId) {
                String readerLogin = param.getValue().getBook().getTitle();
                return new SimpleStringProperty(readerLogin);
            } else {
                return null;
            }
        });

        ObservableList orderList = FXCollections.observableArrayList(userOrders);
        userOrderTable.setItems(orderList);
    }

    private void refreshBooksComboBox() {
        List<Book> allBooks = actions.getAllBooks();
        ObservableList<Book> books = FXCollections.observableArrayList(allBooks);
        comboBookAvailbility.setItems(books);
    }

    @FXML
    private void checkBookAvailbility() {
        List<Integer> collect = actions.getAllOrders().stream().map(orders -> orders.getBook().getId())
                .collect(Collectors.toList());
        int id = comboBookAvailbility.getSelectionModel().getSelectedItem().getId();
        if (collect.contains(id)) {
            availbility.setText("Book isn't available");
        } else {
            availbility.setText("Book is available");
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        stage = (Stage) anchorPane.getScene().getWindow();
        screen = new ScreenController(btnLogout.getScene());

        screen.addScreen("Login", FXMLLoader.load(getClass().getResource("/views/Login.fxml")));
        screen.activate("Login", stage);
    }
}
