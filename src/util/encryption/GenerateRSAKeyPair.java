package util.encryption;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

public class GenerateRSAKeyPair {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private SecretKey secretKey;

    GenerateRSAKeyPair() throws NoSuchAlgorithmException {
        //initialize RSA key
        KeyPairGenerator generateKey = KeyPairGenerator.getInstance("RSA");
        generateKey.initialize(1024); //Initialize key length
        KeyPair pair = generateKey.generateKeyPair();
        //initialize AES key
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        //Set all the key values
        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
        this.secretKey = generator.generateKey();
    }


    //get private key value
    PrivateKey getPrivateKey() {
        return privateKey;
    }
    //get public key value
    PublicKey getPublicKey(){
        return publicKey;
    }
    //get secret AES key value
    SecretKey getSecretKey(){
        return secretKey;
    }



}