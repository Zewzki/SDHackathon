package client.javafx;

import client.control.ClientDriver;
import client.util.ClientNetworking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class FriendsListTabController {

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
    private void friendsListTabButtonClicked(ActionEvent event) throws IOException {
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
    private TextArea friendTextArea;

    @FXML
    private TextField addFriendTextField;

    @FXML
    private Button addFriendButton;

    @FXML
    private void addFriendButtonClicked(ActionEvent event) throws IOException {
        ClientNetworking clientNetworking = new ClientNetworking("127.0.0.1");
        String friend = addFriendTextField.getText();
        clientNetworking.addFriend(LoginInformation.getUsername(), friend);
        clientNetworking.closeConnection();

        Parent viewParent = FXMLLoader.load(getClass().getResource("FriendsListTabFXML.fxml"));
        Scene scene = new Scene(viewParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setTitle("ZelleMoMe Application");
        window.setScene(scene);
        window.show();
    }

    public void initialize() {
        ClientNetworking clientNetworking = new ClientNetworking("127.0.0.1");
        clientNetworking.getFriends(LoginInformation.getUsername());
        ArrayList<ArrayList<Object>> friends = clientNetworking.getQueryResults();
        String friendsList = friends.get(0).get(0).toString();
        friendsList = friendsList.replaceAll(",", "\n");
        friendTextArea.setWrapText(true);
        friendTextArea.setText(friendsList);
        friendTextArea.setEditable(false);
        clientNetworking.closeConnection();
    }
}
