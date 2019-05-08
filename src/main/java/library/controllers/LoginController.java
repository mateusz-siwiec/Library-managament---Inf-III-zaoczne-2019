package library.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LoginController {

    private List<User> users;
    private Actions actions = new Actions();
    private ScreenController screen;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lCredentials;

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button bLogin;
    @FXML
    private Button bRegister;
    @FXML
    private Button bExit;

    private Stage stage;

    public static int id;

    @FXML
    private void initialize() throws Exception {
        actions.initDatabase();
        users = actions.getAllUsers();

        if (users.isEmpty()) {

            User user1 = new User("siwy", "start123", "Mateusz", "Siwiec", 30, 11111111111L, 111111111, "ADMIN");
            User user2 = new User("janek", "start123", "Jan", "Kowalski", 30, 11111111111L, 111111111, "LIBRARIAN");
            User user3 = new User("michu", "start123", "Michal", "Nowak", 30, 11111111111L, 111111111, "READER");


            actions.saveToDatabase(user1);
            actions.saveToDatabase(user2);
            actions.saveToDatabase(user3);

            users = actions.getAllUsers();
        }
    }

    @FXML
    private void login(ActionEvent event) throws IOException {

        stage = (Stage) anchorPane.getScene().getWindow();
        screen = new ScreenController(bLogin.getScene());

        User userToCheck = new User(tfUsername.getText(), pfPassword.getText());

        List<User> userAuth = users.stream()
                .filter(u -> u.getLogin().equals(userToCheck.getLogin()))
                .filter(u -> u.getPassword().equals(userToCheck.getPassword()))
                .collect(Collectors.toList());

        if (!userAuth.isEmpty()) {
            User user = userAuth.get(0);
            System.out.println("User " + user.getLogin() + " logged in as " + user.getRole());

            if (user.getRole().equals("ADMIN")) {
                id = user.getId();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminPane.fxml"));
                screen.addScreen("AdminPane", loader.load());
                AdminPaneController adminPaneController = loader.getController();
                adminPaneController.takeLoggedUserData(user);
                screen.activate("AdminPane", stage);
            }

            if (user.getRole().equals("LIBRARIAN")) {
                id = user.getId();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LibrarianPane.fxml"));
                screen.addScreen("LibrarianPane", loader.load());
                LibrarianPaneController librarianPane = loader.getController();
                librarianPane.takeLoggedUserData(user);
                screen.activate("LibrarianPane", stage);
            }

            if(user.getRole().equals("READER")) {
                id = user.getId();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ReaderPane.fxml"));
                screen.addScreen("ReaderPane", loader.load());
                ReaderPaneController readerPaneController = loader.getController();
                readerPaneController.takeLoggedUserData(user);
                screen.activate("ReaderPane", stage);
            }

        } else {
            lCredentials.setText("Bad credentials");
            lCredentials.setVisible(true);
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) throws IOException {
        stage = (Stage) anchorPane.getScene().getWindow();
        screen = new ScreenController(bLogin.getScene());

        screen.addScreen("Registration", FXMLLoader.load(getClass().getResource("/views/Registration.fxml")));
        screen.activate("Registration", stage);
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
