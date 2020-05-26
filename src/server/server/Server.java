package server.server;

import util.MySQLTool;
import util.encryption.RSAEncryptionTool;

import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final JFrame logFrame;
    private final ServerLog log;
    private final MySQLTool sql;

    private ExecutorService executor;
    private ServerSocket server;
    private SockServer[] sockServer;
    private int counter = 0;
    private int nClientsActive = 0;

    public Server() {

        logFrame = new JFrame("Server Log");
        log = new ServerLog(this);
        sql = new MySQLTool();

        logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logFrame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 1.2), (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.2));
        logFrame.setLocationRelativeTo(null);
        logFrame.add(log);

        sockServer = new SockServer[100];
        executor = Executors.newFixedThreadPool(100);

    }

    public void startServer() {

        logFrame.setVisible(true);
        log.log("Starting server");

        try {
            sql.connect();
        } catch (SQLException e) {
            log.log("Unable to connect to sql database");
        }

        try {
            server = new ServerSocket(23555, 100);

            while (true) {
                try {
                    sockServer[counter] = new SockServer(counter);
                    sockServer[counter].waitForConnection();
                    nClientsActive++;
                    executor.execute(sockServer[counter]);

                }
                catch (EOFException eofException) {
                    log.log("Server terminated connection");
                }
                finally {
                    ++counter;
                    if(counter > sockServer.length) counter = 0;
                }
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        sql.disconnect();

    }

    public MySQLTool getSql() { return sql; }

    private class SockServer implements Runnable {
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private Socket connection;
        private RSAEncryptionTool encryptionTool;
        private int myConID;
        private boolean alive = false;

        public SockServer(int counterIn) {
            myConID = counterIn;
            encryptionTool = new RSAEncryptionTool();
        }

        @Override
        public void run() {

            alive = true;

            while(alive) {

                try {

                    getStreams();

                    while(alive) {

                        try {
                            processConnection();
                        }
                        catch (EOFException eofException) {
                            log.log("Server " + myConID + " terminated connection");
                            alive = false;
                        }

                    }

                    nClientsActive--;

                }
                catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

        }

        private void waitForConnection() throws IOException {
            log.log("Waiting for connection " + myConID);
            connection = server.accept();
            log.log("Connection " + myConID + " received from: " + connection.getInetAddress().getHostName());
        }

        private void getStreams() throws IOException {

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input = new ObjectInputStream(connection.getInputStream());
        }

        private void processConnection() throws IOException {

            try {

                String command = (String) input.readObject();
                String[] commandWords = command.split(" ");

                if(command.equals("TERMINATE")) {
                    closeConnection();
                    return;
                }

                log.log("Server" + myConID + " received: " + command);

                boolean successful = sql.executeCommand(command);

                if(commandWords[0].equals("SELECT")) {

                    if(!successful) sendData(null);

                    log.log("Query successful: " + successful);

                    ResultSet rs = sql.getResultSet();
                    ResultSetMetaData md = sql.getMetaData();

                    sendData(resultSetToList(rs, md));

                }
                else if(commandWords[0].equals("INSERT") || commandWords[0].equals("UPDATE")) {

                    log.log("Insert/Update successful: " + successful);

                }
                else {
                    log.log("Server" + myConID + " recieved unrecognized command: " + command);
                }


            } catch (ClassNotFoundException classNotFoundException) {
                log.log("Server" + myConID + " received unknown object type");
            } catch (SQLException e) {
                log.log("Error Accessing Meta Data");
                e.printStackTrace();
            }

        }

        private void closeConnection() {
            alive = false;
            sendData("TERMINATE");
            log.log("Terminating connection " + myConID);

            try {
                output.close();
                input.close();
                connection.close();
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        private void sendData(Object data) {
            try {
                output.writeObject(data);
                output.flush();
                log.log("Server" + myConID + " sent " + data.toString());
            }
            catch (IOException e) {
                log.log("Server" + myConID + " failed to write data " + data.toString());
                e.printStackTrace();
            }
        }

    }

    private ArrayList<ArrayList<Object>> resultSetToList(ResultSet rs, ResultSetMetaData md) throws SQLException{

        ArrayList<ArrayList<Object>> resultArr = new ArrayList<>();
        for(int i = 0; i < md.getColumnCount(); i++) {

            ArrayList<Object> column = new ArrayList<>();
            rs.last();
            int numOfRows = rs.getRow();
            rs.first();

            for(int j = 0; j < numOfRows; j++) {
                rs.absolute(j + 1);
                column.add(rs.getObject(i + 1));
            }

            resultArr.add(column);

        }
        return resultArr;
    }

}
