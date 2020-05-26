package client.javafx;

import client.util.ClientNetworking;
import client.util.Transaction;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TransactionHistoryTabController {

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
    private MenuButton sortBySendOrReceivedMenuButton;

    @FXML
    private MenuButton sortByTypeMenuButton;

    @FXML
    private TextArea transactionTextArea;

    @FXML
    private MenuButton displayChoiceMenuButton;

    @FXML
    private MenuItem yoursChoiceMenuItem;

    @FXML
    private MenuItem friendsChoiceMenuItem;

    @FXML
    private TextField friendsTextField;

    @FXML
    private Button findFriendButton;

    @FXML
    private void findFriendButtonClicked(ActionEvent event){
        ClientNetworking clientNetworking = new ClientNetworking("127.0.0.1");

        if(!clientNetworking.getFriends(friendsTextField.getText())){
            friendsTextField.setText("User does not exist!");
            return;
        }
        String friends = clientNetworking.getQueryResults().get(0).get(0).toString();
        if(friends.contains(LoginInformation.getUsername())){
            clientNetworking.getUserTransactionHistory(friendsTextField.getText());
            ArrayList<ArrayList<Object>> contents = clientNetworking.getQueryResults();
            StringBuffer stringBuffer = new StringBuffer();

            for(int i = 0; i < contents.get(0).size(); i++) {
                String sender = contents.get(0).get(i).toString();
                String recipient = contents.get(1).get(i).toString();
                String amount = contents.get(2).get(i).toString();
                String timeStamp = contents.get(3).get(i).toString();
                String comment = contents.get(4).get(i).toString();
                stringBuffer.append(sender + ", " + recipient + ", " + amount + ", " + timeStamp + ", " + comment + "\n");
            }

            transactionTextArea.setWrapText(true);
            transactionTextArea.setText(stringBuffer.toString());
            System.out.println(stringBuffer.toString());
            clientNetworking.closeConnection();
        }else{
            friendsTextField.setText("Not Friends!");
        }

    }

    @FXML
    private void yoursChoiceMenuItemClicked(ActionEvent event){
        ClientNetworking clientNetworking = new ClientNetworking("127.0.0.1");
        clientNetworking.getUserTransactionHistory(LoginInformation.getUsername());
        ArrayList<ArrayList<Object>> contents = clientNetworking.getQueryResults();
        StringBuffer stringBuffer = new StringBuffer();

        for(int i = 0; i < contents.get(0).size(); i++) {
            String sender = contents.get(0).get(i).toString();
            String recipient = contents.get(1).get(i).toString();
            String amount = contents.get(2).get(i).toString();
            String timeStamp = contents.get(3).get(i).toString();
            String comment = contents.get(4).get(i).toString();
            stringBuffer.append(sender + ", " + recipient + ", " + amount + ", " + timeStamp + ", " + comment + "\n");
        }

        transactionTextArea.setWrapText(true);
        transactionTextArea.setText(stringBuffer.toString());
        System.out.println(stringBuffer.toString());
        clientNetworking.closeConnection();

        displayChoiceMenuButton.setText("Yours");
    }

    @FXML
    private void friendsChoiceMenuItemClicked(ActionEvent event){
        displayChoiceMenuButton.setText("A Friend's");
    }

    public void initialize() {
        ClientNetworking clientNetworking = new ClientNetworking("127.0.0.1");
        clientNetworking.getUserTransactionHistory(LoginInformation.getUsername());
        ArrayList<ArrayList<Object>> contents = clientNetworking.getQueryResults();
        StringBuffer stringBuffer = new StringBuffer();

        try {
            for (int i = 0; i < contents.get(0).size(); i++) {
                String sender = contents.get(0).get(i).toString();
                String recipient = contents.get(1).get(i).toString();
                String amount = contents.get(2).get(i).toString();
                String timeStamp = contents.get(3).get(i).toString();
                String comment = contents.get(4).get(i).toString();
                stringBuffer.append(sender + ", " + recipient + ", " + amount + ", " + timeStamp + ", " + comment + "\n");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return;
        }



        transactionTextArea.setWrapText(true);
        transactionTextArea.setText(stringBuffer.toString());
        transactionTextArea.setEditable(false);
        System.out.println(stringBuffer.toString());
        clientNetworking.closeConnection();

        displayChoiceMenuButton.setText("Yours");
    }
}
