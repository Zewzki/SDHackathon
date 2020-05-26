package util;

import java.sql.*;

public class MySQLTool {

    private static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class021";
    private static final String USERNAME = "engr_class021";
    private static final String PASSWORD = "abcd1234";

    private static String QUERY;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;

    private boolean connectedToDatabase = false;

    public MySQLTool() {}

    public void connect() throws SQLException{
        connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDatabase = true;
    }

    public boolean executeCommand(String command) {

        String[] commandWords = command.split(" ");

        if(commandWords[0].equals("SELECT")) return query(command);
        else if(commandWords[0].equals("INSERT") || commandWords[0].equals("UPDATE")) return update(command) != 0;
        else return false;

    }

    public boolean query(String query) {

        if(!connectedToDatabase) throw new IllegalStateException("Not Connected to Database");

        try {

            resultSet = statement.executeQuery(query);

            metaData = resultSet.getMetaData();

            // determine number of rows in ResultSet
            resultSet.last(); // move to last row
            numberOfRows = resultSet.getRow(); // get row number

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public int update(String update) {

        if(!connectedToDatabase) throw new IllegalStateException("Not Connected to Database");

        try {

            int rowsAffected = statement.executeUpdate(update);
            System.out.println(update + " : " + rowsAffected);
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public void disconnect() {

        if(!connectedToDatabase) return;

        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectedToDatabase = false;
        }

    }

    public ResultSet getResultSet() { return resultSet; }
    public ResultSetMetaData getMetaData() { return metaData; }

}
