package library.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import library.entities.Book;
import library.entities.Orders;
import library.entities.User;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class LibrarianPaneController implements Initializable {

    private int userId;

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfYearOfPublish;
    @FXML
    private TextField tfNewTitle;
    @FXML
    private TextField tfNewAuthor;
    @FXML
    private TextField tfNewYearOfPublish;
    @FXML
    private ComboBox<User> comboUsers;
    @FXML
    private ComboBox<Book> comboBooks;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnDeleteBook;
    @FXML
    private Button btnEditBook;
    @FXML
    private TableColumn<Book, String> columnTitle;
    @FXML
    private TableColumn<Book, String> columnAuthor;
    @FXML
    private TableColumn<Book, String> columnYearOfPublish;
    @FXML
    private TableColumn<Orders, String> columnReaderLogin;
    @FXML
    private TableColumn<Orders, String> columnReaderName;
    @FXML
    private TableColumn<Orders, String> columnReaderSurname;
    @FXML
    private TableColumn<Orders, String> columnOrderBookTitle;
    @FXML
    private TableColumn<Orders, String> columnOrderBookAuthor;
    @FXML
    private TableColumn<Orders, String> columnDateFrom;
    @FXML
    private TableColumn<Orders, String> columnDateTo;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableView<Orders> ordersTable;
    @FXML
    private Label labelInfo;
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
    private Button btnLogout;
    @FXML
    private AnchorPane anchorPane;

    private ScreenController screen;

    private Stage stage;

    private ObservableList<Book> ObservableListBooks;


    private Actions actions = new Actions();


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId = LoginController.id;
        actions.initDatabase();
        loadBookDataFromDatabaseIntoTable(null);
        loadOrderDataFromDatabaseIntoTable(null);
        refreshBookTable();
        refreshUsersComboBox();
        refreshBooksComboBox();
    }

    private void refreshBooksComboBox() {
        List<Book> allBooks = actions.getAllBooks();
        ObservableList<Book> books = FXCollections.observableArrayList(allBooks);
        comboBooks.setItems(books);
    }

    private void refreshUsersComboBox() {
        List<User> allUsers = actions.getAllUsers();
        ObservableList<User> users = FXCollections.observableArrayList(allUsers);
        comboUsers.setItems(users);
    }

    @FXML
    private void addBook(ActionEvent event) {
        String author = tfAuthor.getText();
        String title = tfTitle.getText();
        int yearOfPublish = Integer.parseInt(tfYearOfPublish.getText());
        Book book = new Book(title, author, yearOfPublish);
        actions.saveToDatabase(book);
        refreshBookTable();
        tfTitle.clear();
        tfYearOfPublish.clear();
        tfAuthor.clear();
        refreshBooksComboBox();
    }

    @FXML
    private void loadBookDataFromDatabaseIntoTable(ActionEvent event) {
        Transaction transaction = actions.session.beginTransaction();
        List allBooks = actions.session.createCriteria(Book.class).list();
        transaction.commit();

        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnYearOfPublish.setCellValueFactory(new PropertyValueFactory<>("yearOfPublish"));
        bookTable.setItems(ObservableListBooks);

        ObservableList booksList = FXCollections.observableArrayList(allBooks);
        bookTable.setItems(booksList);
    }


    @FXML
    private void loadOrderDataFromDatabaseIntoTable(ActionEvent event) {
        Transaction transaction = actions.session.beginTransaction();
        List allOrders = actions.session.createCriteria(Orders.class).list();
        transaction.commit();

        columnReaderLogin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getUser().getLogin();
                return new SimpleStringProperty(readerLogin);
            }
        });
        columnReaderName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getUser().getName();
                return new SimpleStringProperty(readerLogin);
            }
        });
        columnReaderSurname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getUser().getSurname();
                return new SimpleStringProperty(readerLogin);
            }
        });
        columnDateTo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getDateTo();
                return new SimpleStringProperty(readerLogin);
            }
        });
        columnDateFrom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getDateFrom();
                return new SimpleStringProperty(readerLogin);
            }
        });
        columnOrderBookAuthor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getBook().getAuthor();
                return new SimpleStringProperty(readerLogin);
            }
        });
        columnOrderBookTitle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Orders, String> param) {
                String readerLogin = param.getValue().getBook().getTitle();
                return new SimpleStringProperty(readerLogin);
            }
        });
        ObservableList orderList = FXCollections.observableArrayList(allOrders);
        ordersTable.setItems(orderList);
    }

    @FXML
    private void refreshBookTable() {
        Transaction transaction = actions.session.beginTransaction();
        List allBooks = actions.session.createCriteria(Book.class).list();
        transaction.commit();

        ObservableList booksList = FXCollections.observableArrayList(allBooks);
        bookTable.setItems(booksList);
        bookTable.refresh();
    }

    @FXML
    private void refreshOrderTable() {
        Transaction transaction = actions.session.beginTransaction();
        List allOrders = actions.session.createCriteria(Orders.class).list();
        transaction.commit();

        ObservableList orderList = FXCollections.observableArrayList(allOrders);
        ordersTable.setItems(orderList);
        ordersTable.refresh();
    }

    @FXML
    private void handleBookTable(MouseEvent event) {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String yearOfPublish = String.valueOf(selected.getYearOfPublish());

            tfNewYearOfPublish.setText(yearOfPublish);
            tfNewTitle.setText(selected.getTitle());
            tfNewAuthor.setText(selected.getAuthor());
        }
        refreshBookTable();
    }

    @FXML
    private void editBook(ActionEvent event) {
        String title = tfNewTitle.getText();
        String author = tfNewAuthor.getText();
        String yearOfPublish = tfNewYearOfPublish.getText();
        int intYear = Integer.parseInt(yearOfPublish);
        int id = bookTable.getSelectionModel().getSelectedItem().getId();

        Book editBook = new Book(id, title, author, intYear);
        actions.updateObject(editBook);
        clearBookFields();
        refreshBookTable();
        refreshBooksComboBox();
    }

    @FXML
    private void deleteBook(ActionEvent event) {
        Book book = bookTable.getSelectionModel().getSelectedItem();
        actions.deleteFromDatabase(book);
        refreshBookTable();
        clearBookFields();
        refreshBooksComboBox();
    }

    @FXML
    private void clearBookFields() {
        tfNewYearOfPublish.clear();
        tfNewAuthor.clear();
        tfNewTitle.clear();
    }

    @FXML
    private void addOrder(ActionEvent event) {
        User user = comboUsers.getSelectionModel().getSelectedItem();
        Book book = comboBooks.getSelectionModel().getSelectedItem();
        List<Integer> allBookIds = actions.getAllBookIds();

        if (!allBookIds.contains(book.getId())) {
            String stringDateFrom = getDate(dateFrom);
            String stringDateTo = getDate(dateTo);
            Orders orders = new Orders(user, book, stringDateFrom, stringDateTo);
            actions.saveToDatabase(orders);
            labelInfo.setText("Order successfully");
        } else {
            labelInfo.setText("The book is currently borrowed");
        }
        refreshBookTable();
        refreshUsersComboBox();
        refreshBooksComboBox();
        refreshOrderTable();
    }

    @FXML
    private void deleteOrder(ActionEvent event) {
        Orders order = ordersTable.getSelectionModel().getSelectedItem();
        actions.deleteFromDatabase(order);
        refreshOrderTable();
    }

    private String getDate(DatePicker dateTo) {
        LocalDate localDate = dateTo.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return date.toString();
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


            User userEdit = new User(userId, username, password, firstname, surname, age, pesel, phone, "LIBRARIAN");

            actions.updateObject(userEdit);

            tfName.setDisable(true);
            tfSurname.setDisable(true);
            tfAge.setDisable(true);
            tfPhoneNumber.setDisable(true);
            tfPassword.setDisable(true);
            tfUsername.setDisable(true);
            tfPesel.setDisable(true);
        }

        refreshOrderTable();

    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        stage = (Stage) anchorPane.getScene().getWindow();
        screen = new ScreenController(btnLogout.getScene());

        screen.addScreen("Login", FXMLLoader.load(getClass().getResource("/views/Login.fxml")));
        screen.activate("Login", stage);
    }
}
