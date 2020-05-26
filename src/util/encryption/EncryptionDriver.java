package util.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

//Generate public and private keys for clients and server
public class EncryptionDriver {
    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, SignatureException {
        //Add key pair generator
        //Add client private key, public key and server public key to client
        GenerateRSAKeyPair shareKeyPairGenerator = new GenerateRSAKeyPair();
        RSAEncryptionTool encryption = new RSAEncryptionTool();


        //Test user input string
        String userInput = "\"I feel most alive when rapidly approaching my death\"";
        System.out.println("User input text is  " + userInput);

        //1. Sever sends RSA encrypted message and generates AES key and send it to client, and signs it
        byte [] encryptText = encryption.encryptInput(userInput,shareKeyPairGenerator.getSecretKey());
        String userSign = encryption.signature("Good",shareKeyPairGenerator.getPrivateKey());
        System.out.println("Public Key: " + shareKeyPairGenerator.getPublicKey());
        System.out.println("Private Key: " + shareKeyPairGenerator.getPrivateKey());
        System.out.println("Secret Key: " + shareKeyPairGenerator.getSecretKey());

        //prints out signature
        System.out.println("User signature is : " + "Good");

        //2. Server encrypts RSA public key with AES key and sent it to client
        byte [] encryptedKey = encryption.encryptKey(shareKeyPairGenerator.getPublicKey(),shareKeyPairGenerator.getSecretKey());
        System.out.println("Encrypted key is : " + new String(encryptedKey, StandardCharsets.UTF_8));

        //3.client decrypts RSA public key and decrypts the message using decrypted key
        String finalResult = encryption.decrypt(encryptText,encryptedKey,shareKeyPairGenerator.getPrivateKey());

        //display the message, verifying user identity
        if (encryption.verify("blind",userSign,shareKeyPairGenerator.getPublicKey()))
        {
            System.out.println("Decrypted text: " + finalResult);}
        else
        {System.out.println("Wrong signature !");}

        /*

        GenerateRSAKeyPair keyPair = new GenerateRSAKeyPair();
        String userInput = "\"I feel most alive when rapidly approaching my death\"";
        System.out.println("Before encrypt: " + userInput);
        String encryptedText = new String((encryptInput(userInput,keyPair.getSecretKey())), StandardCharsets.UTF_8);
        System.out.println("Encrypted: " +encryptedText);
        System.out.println("Final : " + decrypt((encryptInput(userInput,keyPair.getSecretKey())),
                (encryptKey(keyPair.getPublicKey(),keyPair.getSecretKey())),keyPair.getPrivateKey()));


        //Let's sign our message
        String signature = signature("Caustic", keyPair.getPrivateKey());

        //check signature
        boolean isCorrect = verify("Caustic", signature, keyPair.getPublicKey());
        System.out.println("Signature correct: " + isCorrect);
         */

    }
}


