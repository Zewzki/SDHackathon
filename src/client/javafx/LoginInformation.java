package client.javafx;

public class LoginInformation {
    private static String username;
    private static String password;
    public static final String[] transactionColumnTitles = new String[] {"Sender", "Recipient", "Amount", "Timestamp", "Comment"};

    public static void setUsername(String givenUsername){
        username = givenUsername;
    }

    public static void setPassword(String givenPassword){
        password = givenPassword;
    }

    public static String getUsername(){
        return username;
    }

    public static String getPassword(){
        return password;
    }
}
