package client.javafx;


import client.util.ClientNetworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class UserRegisterNewAccountController {

    @FXML
    private Button backToUserLoginButton;

    @FXML
    private TextField enterUsernameTextField;

    @FXML
    private PasswordField enterPasswordTextField;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private Button submitNewUserRegistrationButton;

    @FXML
    private void setSubmitNewUserRegistrationButtonClicked(ActionEvent event) throws IOException {
        String username = enterUsernameTextField.getText();
        String password = enterPasswordTextField.getText();
        String passwordConfirm = confirmPasswordTextField.getText();
        if(password.equals(passwordConfirm)){
            ClientNetworking register = new ClientNetworking("127.0.0.1");
            if(register.registerUser(username, password)){
                Parent userLoginViewParent = FXMLLoader.load(getClass().getResource("UserLoginFXML.fxml"));
                Scene userLoginScene = new Scene(userLoginViewParent);

                Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

                window.setTitle("User Login");
                window.setScene(userLoginScene);
                window.show();
            }
            register.closeConnection();
        }

    }

    @FXML
    private void backToUserLoginButtonClicked(ActionEvent event) throws IOException {
        Parent userLoginViewParent = FXMLLoader.load(getClass().getResource("UserLoginFXML.fxml"));
        Scene userLoginScene = new Scene(userLoginViewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("User Login");
        window.setScene(userLoginScene);
        window.show();
    }

    public void initialize() {

    }
}
