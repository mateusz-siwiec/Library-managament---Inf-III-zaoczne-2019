package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import library.entities.Book;
import library.entities.Orders;
import library.entities.User;
import org.hibernate.Transaction;

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
    private TableView<Book> bookTable;
    @FXML
    private Label labelInfo;

    private ObservableList<Book> ObservableListBooks;

    private Actions actions = new Actions();


    public void takeLoggedUserData(User user) {
        userId = user.getId();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actions.initDatabase();
        loadBookDataFromDatabaseIntoTable(null);
        refreshBookTable();

        List<User> allUsers = actions.getAllUsers();
        ObservableList<User> users = FXCollections.observableArrayList(allUsers);
        comboUsers.setItems(users);

        List<Book> allBooks = actions.getAllBooks();
        ObservableList<Book> books = FXCollections.observableArrayList(allBooks);
        comboBooks.setItems(books);
//        columnAuthor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
//                if (param.getValue() != null) {
//                    return new SimpleStringProperty(param.getValue().getAuthor());
//                }
//                else {
//                    return null;
//                }
//            }
//        });
    }

    @FXML
    private void addBook(ActionEvent event) {
        String author = tfAuthor.getText();
        String title = tfTitle.getText();
        int yearOfPublish = Integer.parseInt(tfYearOfPublish.getText());
        Book book = new Book(author, title, yearOfPublish);
        actions.saveToDatabase(book);
        refreshBookTable();
        tfTitle.clear();
        tfYearOfPublish.clear();
        tfAuthor.clear();
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
    private void refreshBookTable() {
        Transaction transaction = actions.session.beginTransaction();
        List allBooks = actions.session.createCriteria(Book.class).list();
        transaction.commit();

        ObservableList booksList = FXCollections.observableArrayList(allBooks);
        bookTable.setItems(booksList);
        bookTable.refresh();
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
    }

    @FXML
    private void deleteBook(ActionEvent event) {
        Book book = bookTable.getSelectionModel().getSelectedItem();
        actions.deleteFromDatabase(book);
        refreshBookTable();
        clearBookFields();
    }

    @FXML
    private void clearBookFields() {
        tfNewYearOfPublish.clear();
        tfNewAuthor.clear();
        tfNewTitle.clear();
    }

    @FXML
    private void addOrder(ActionEvent event){
        User user = comboUsers.getSelectionModel().getSelectedItem();
        Book book = comboBooks.getSelectionModel().getSelectedItem();
        List<Integer> allBookIds = actions.getAllBookIds();

        if(!allBookIds.contains(book.getId())) {
            String stringDateFrom = getDate(dateFrom);
            String stringDateTo = getDate(dateTo);
            Orders orders = new Orders(user, book, stringDateFrom, stringDateTo);
            actions.saveToDatabase(orders);
        } else {
            labelInfo.setText("The book is currently borrowed");
        }
        refreshBookTable();
    }

    private String getDate(DatePicker dateTo) {
        LocalDate localDate = dateTo.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return date.toString();
    }

}
