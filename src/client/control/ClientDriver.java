package client.control;

import client.util.ClientNetworking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ClientDriver {

    public static void main(String[] args) {

        ClientNetworking cn = new ClientNetworking("127.0.0.1");

        //cn.registerUser("")

        //cn.closeConnection();

    }

    public static void printQueryResult(ArrayList<ArrayList<Object>> q) {

        for(int i = 0; i < q.size(); i++) {
            ArrayList<Object> column = q.get(i);
            for(int j = 0; j < column.size(); j++) {
                System.out.print(column.get(j).toString() + ", ");
            }
            System.out.println();
        }

    }

}
