package client.javafx;

import client.util.ClientNetworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class AddFundsController  {

    @FXML
    private Button profileButton;

    @FXML
    private void profileButtonClicked(ActionEvent event) throws IOException {
        Parent viewParent = FXMLLoader.load(getClass().getResource("ProfileTabFXML.fxml"));
        Scene scene = new Scene(viewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("ZelleMoMe Application");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private Button transactionHistoryButton;

    @FXML
    private void transactionHistoryButtonClicked(ActionEvent event) throws IOException {
        Parent viewParent = FXMLLoader.load(getClass().getResource("TransactionHistoryTabFXML.fxml"));
        Scene scene = new Scene(viewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("ZelleMoMe Application");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private Button sendMoneyButton;

    @FXML
    private void sendMoneyButtonClicked(ActionEvent event) throws IOException {
        Parent viewParent = FXMLLoader.load(getClass().getResource("SendMoneyTabFXML.fxml"));
        Scene scene = new Scene(viewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("ZelleMoMe Application");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private Button friendsListTabButton;

    @FXML
    private void friendsListTabButton(ActionEvent event) throws IOException {
        Parent viewParent = FXMLLoader.load(getClass().getResource("FriendsListTabFXML.fxml"));
        Scene scene = new Scene(viewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("ZelleMoMe Application");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private Button settingsButton;

    @FXML
    private void settingsButtonClicked(ActionEvent event) throws IOException {
        Parent viewParent = FXMLLoader.load(getClass().getResource("SettingsTabFXML.fxml"));
        Scene scene = new Scene(viewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("ZelleMoMe Application");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private Button logoutButton;

    @FXML
    private void logoutButtonClicked(ActionEvent event) throws IOException {
        Parent userLoginViewParent = FXMLLoader.load(getClass().getResource("UserLoginFXML.fxml"));
        Scene userLoginScene = new Scene(userLoginViewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("User Login");
        window.setScene(userLoginScene);
        window.show();
    }

    @FXML
    private TextField amountTextField;

    @FXML
    private MenuButton optionalPaymentsMenuButton;

    @FXML
    private Button addFundsButton;

    @FXML
    private void addFundsButtonClicked(ActionEvent event){
        Double amount = Double.parseDouble(amountTextField.getText());
        ClientNetworking addFunds = new ClientNetworking("127.0.0.1");
        addFunds.addFunds(LoginInformation.getUsername(), amount);
        addFunds.closeConnection();

        amountTextField.setText("");
    }


    public void initialize() {

    }

}
