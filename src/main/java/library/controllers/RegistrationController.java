package library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.entities.User;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    private Actions actions = new Actions();
    private ScreenController screen;


    @FXML
    private AnchorPane ap;

    @FXML
    private Button bRegister;
    @FXML
    private Button bBack;

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfPesel;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfAge;
    @FXML
    private Label labelWrong;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actions.initDatabase();
    }

    @FXML
    public void register(ActionEvent event) {
            User user = new User(
                    tfLogin.getText(),
                    actions.get_SHA_512_SecurePassword(tfPassword.getText(), "securePassword"),
                    tfName.getText(),
                    tfSurname.getText(),
                    Integer.parseInt(tfAge.getText()),
                    Long.parseLong(tfPesel.getText()),
                    Integer.parseInt(tfPhoneNumber.getText()), "READER");

            actions.saveToDatabase(user);
            System.out.println("Registered " + user.getLogin() + ": " + user.getName() + " " + user.getSurname());
            clearRegisterFields();
            labelWrong.setVisible(false);
        }
    @FXML
    public void backToLogin(ActionEvent event) throws IOException {
        stage = (Stage) ap.getScene().getWindow();
        screen = new ScreenController(bBack.getScene());

        screen.addScreen("Login", FXMLLoader.load(getClass().getResource("/views/Login.fxml")));
        screen.activate("Login", stage);
    }

    public void clearRegisterFields() {
        tfAge.clear();
        tfLogin.clear();
        tfName.clear();
        tfSurname.clear();
        tfPesel.clear();
        tfPhoneNumber.clear();
        tfPassword.clear();
    }
}
