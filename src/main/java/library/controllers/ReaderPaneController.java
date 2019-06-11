package library.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import library.config.DBConfForRaports;
import library.entities.Book;
import library.entities.Orders;
import library.entities.User;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @FXML
    private void generateRaport(ActionEvent event) throws FileNotFoundException, DocumentException, SQLException {
        String fileName = takePath() + "\\reports.pdf";
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        DBConfForRaports dbConfForRaports = new DBConfForRaports();
        Connection connection = dbConfForRaports.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String orderQuery = "select u.name , u.surname, b.title, b.author ,o.dateFrom , o.dateTo  from User u, Orders o, Book b where o.user_id=u.id AND b.id = o.book_id";
        ps = connection.prepareStatement(orderQuery);
        rs = ps.executeQuery();
        PdfPTable table = new PdfPTable(6);
        createCells(table);
        table.setHeaderRows(1);

        while (rs.next()) {
            table.addCell(rs.getString("name"));
            table.addCell(rs.getString("surname"));
            table.addCell(rs.getString("title"));
            table.addCell(rs.getString("author"));
            table.addCell(rs.getString("dateFrom"));
            table.addCell(rs.getString("dateTo"));
        }
        document.add(table);
        document.newPage();
        document.close();
    }

    private void createCells(PdfPTable table) {
        createSingleCell(table, "Name");
        createSingleCell(table, "Surname");
        createSingleCell(table, "Book title");
        createSingleCell(table, "Book author");
        createSingleCell(table, "Date from");
        createSingleCell(table, "Date to");
    }

    /**
     * Metoda do tworzenia pojedynczej komórki w pliku pdf
     *
     * @param table
     * @param tableName
     */
    private void createSingleCell(PdfPTable table, String tableName) {
        PdfPCell c1 = new PdfPCell(new Phrase(tableName));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
    }

    private String takePath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            //No Directory selected
            return null;
        } else {
            System.out.println(selectedDirectory.getAbsolutePath());
        }
        return selectedDirectory.getAbsolutePath().toString();
    }
}
