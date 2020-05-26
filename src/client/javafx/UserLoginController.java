package client.javafx;

import client.util.ClientNetworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class UserLoginController {

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button registerNewAccountButton;

    @FXML
    private TextArea generalTextArea;

    @FXML
    private Button loginButton;

    @FXML
    private Label wrongInfoLabel;

    @FXML
    private void passwordTextFieldEntered(ActionEvent event){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

    }

    @FXML
    private void registerNewUserButtonEntered(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();


        Parent userRegistrationViewParent = FXMLLoader.load(getClass().getResource("UserRegisterNewAccountFXML.fxml"));
        Scene userRegistrationScene = new Scene(userRegistrationViewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("User Registration");
        window.setScene(userRegistrationScene);
        window.show();
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException {
        ClientNetworking login = new ClientNetworking("127.0.0.1");
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(login.loginUser(username, password)){
            LoginInformation.setUsername(username);
            LoginInformation.setPassword(password);

            Parent applicationViewParent = FXMLLoader.load(getClass().getResource("ProfileTabFXML.fxml"));
            Scene applicationScene = new Scene(applicationViewParent);

            Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

            window.setTitle("ZelleMoMe Application");
            window.setScene(applicationScene);
            window.show();
        }
        else wrongInfoLabel.setVisible(true);
        login.closeConnection();
    }

    // called by FXMLLoader to initialize the controller
    public void initialize() {
        wrongInfoLabel.setVisible(false);
    }


}
