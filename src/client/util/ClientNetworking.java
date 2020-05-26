package client.util;

import util.encryption.RSAEncryptionTool;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class ClientNetworking {

    public enum POST_TRANSACTION_MESSAGE { TRANSACTION_COMPLETED, INSUFFICIENT_FUNDS, INVALID_RECIPIENT, INVALID_SENDER}

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String host = "";
    private Socket client;
    private RSAEncryptionTool encryptionTool;
    private Logger logger;
    private ArrayList<ArrayList<Object>> queryResults;

    public ClientNetworking(String host) {
        this.host = host;
        logger = new Logger();
        logger.setLogToConsole(true);

        try {
            connectToServer();

        } catch (IOException e) {
            System.out.println("Could not connect to host: " + host);
            return;
        }

        try {
            getStreams();
        } catch (IOException e) {
            logger.log("Could not get socket streams...");
            return;
        }

    }

    private void connectToServer() throws IOException {
        logger.log("Attempting connection");
        client = new Socket(InetAddress.getByName(host), 23555);
        logger.log("Connected to: " + client.getInetAddress().getHostName());
    }

    private void getStreams() throws IOException {
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
        logger.log("Got I/O streams");
    }

    public POST_TRANSACTION_MESSAGE postTransaction(String senderUsername, double amount, String recipientUsername, String comment, String type) {

        String checkBalance = "SELECT balance from userInfo where username like '" + senderUsername + "'";
        String checkRecipient = "SELECT username, balance from userInfo where username like '" + recipientUsername + "'";

        executeQuery(checkBalance);

        if(queryResults.get(0).size() == 0) return POST_TRANSACTION_MESSAGE.INVALID_SENDER;

        double senderBalance = (Double) queryResults.get(0).get(0);

        if(senderBalance < amount) return POST_TRANSACTION_MESSAGE.INSUFFICIENT_FUNDS;

        executeQuery(checkRecipient);

        if(queryResults.get(0).size() == 0) return POST_TRANSACTION_MESSAGE.INVALID_RECIPIENT;

        double recipientBalance = (Double) queryResults.get(1).get(0);

        recipientBalance += amount;
        senderBalance -= amount;

        Calendar cal = Calendar.getInstance();
        Timestamp dateTime = new Timestamp(cal.getTimeInMillis());

        //UPDATE `userInfo` SET `balance`=80.00 WHERE username LIKE 'testUsername'
        String updateSenderBalance = "UPDATE userInfo SET balance=" + senderBalance + " where username like '" + senderUsername + "'";
        String updateRecipientBalance = "UPDATE userInfo SET balance=" + recipientBalance + " where username like '" + recipientUsername + "'";
        String postTransactionRecord = "INSERT INTO transactionHistory (sender, recipient, amount, timestamp, comment) VALUES ('" + senderUsername + "', '" + recipientUsername + "', " + amount + ", '" + dateTime + "', '" + comment + "')";

        executeUpdate(updateSenderBalance);
        executeUpdate(updateRecipientBalance);
        executeUpdate(postTransactionRecord);

        return POST_TRANSACTION_MESSAGE.TRANSACTION_COMPLETED;

    }

    public boolean loginUser(String username, String password) {

        String q = "SELECT password from userInfo where username like '" + username + "'";

        executeQuery(q);

        String hashedPassword = hashAndSaltPassword(password);

        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        String storedPassword = queryResults.get(0).get(0).toString();

        if(hashedPassword.equals(storedPassword)) return true;

        return false;

    }

    public boolean registerUser(String username, String password) {

        executeQuery("SELECT username FROM userInfo where username like '" + username + "'");

        if(queryResults == null || queryResults.get(0).size() != 0) return false;

        executeQuery("SELECT username FROM userInfo");

        password = hashAndSaltPassword(password);
        int newIDValue = queryResults.get(0).size() + 1;
        String addCommand = "INSERT INTO userInfo (username, password, userID, balance, friends) VALUES ('" + username + "', '" + password + "', " + newIDValue + ", 0.00, '')";

        executeUpdate(addCommand);

        return true;

    }

    public boolean addFunds(String username, double amount) {

        if(amount <= 0.00) return false;

        String getUser = "SELECT username, balance from userInfo where username like '" + username + "'";
        executeQuery(getUser);

        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        double balance = (Double) queryResults.get(1).get(0);
        balance += amount;

        String updateBalance = "UPDATE userInfo SET balance=" + balance + " where username like '" + username + "'";
        executeUpdate(updateBalance);

        return true;

    }

    public boolean getUserInfo(String username) {

        String getUser = "SELECT username, balance from userInfo where username like '" + username + "'";
        executeQuery(getUser);

        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        return true;

    }

    public boolean getUserTransactionHistory(String username) {

        String userTransactionHistory = "SELECT * from transactionHistory where (sender like '" + username + "') OR (recipient like '" + username + "')";
        executeQuery(userTransactionHistory);

        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        return true;

    }

    public boolean getFriends(String username) {

        String getFriendsCommand = "SELECT friends from userInfo where username like '" + username + "'";

        executeQuery(getFriendsCommand);

        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        return true;

    }

    public boolean addFriend(String username, String friend) {

        String validateUserCommand = "SELECT username from userInfo where username like '" + username + "'";
        executeQuery(validateUserCommand);
        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        String validateFriendCommand = "SELECT username from userInfo where username like '" + friend + "'";

        executeQuery(validateFriendCommand);

        if(queryResults == null || queryResults.get(0).size() == 0) return false;

        if (!getFriends(username)) return false;

        String friendsList = queryResults.get(0).get(0).toString();
        if(friendsList.contains(friend)) return false;
        friendsList += friend + ",";

        String updateFriendsListCommand = "UPDATE userInfo SET friends='" + friendsList + "' where username like '" + username + "'";

        executeUpdate(updateFriendsListCommand);

        return true;

    }

    public void executeCommand(String command) {

        String[] commandWords = command.split(" ");

        if(commandWords[0].equals("SELECT")) executeQuery(command);
        else if(commandWords[0].equals("INSERT") || commandWords[0].equals("UPDATE")) executeUpdate(command);
        else logger.log("Unrecognized command: " + command);

    }

    private void executeQuery(String query) {

        try {

            sendData(query);

            queryResults = (ArrayList<ArrayList<Object>>) input.readObject();
            if(queryResults != null) logger.log("Received: " + queryResults.toString());

        } catch (ClassNotFoundException classNotFoundException) {
            logger.log("Unknown object type received");
        } catch (IOException e) {
            logger.log("Error reading from socket");
            e.printStackTrace();
        }

    }

    private void executeUpdate(String update) {

        sendData(update);

    }

    public void closeConnection() {
        sendData("TERMINATE");
        logger.log("Closing connection");
        try {
            output.close();
            input.close();
            client.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void sendData(String message) {
        try {
            output.writeObject(message);
            output.flush();
            logger.log("Wrote: " + message);
        } catch (IOException ioException) {
            logger.log("Error writing object");
        }
    }

    private String hashAndSaltPassword(String password) {

        password += "&#fj12)z";

        String hashedPassword = "";

        try {

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = password.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            for(int i = 0; i < passHash.length; i++) hashedPassword += passHash[i];

        } catch (NoSuchAlgorithmException e) {
            logger.log("Error performing hash operations");
            return "";
        }

        return hashedPassword;

    }

    public ArrayList<ArrayList<Object>> getQueryResults() { return queryResults; }

}
