package util.encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class RSAEncryptionTool {

    public RSAEncryptionTool(){}

    //Encrypt user input using AES
    byte[] encryptInput(String data, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //initialize user text
        Cipher userTextCipher = Cipher.getInstance("AES");
        userTextCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] encryptedText;
        encryptedText = userTextCipher.doFinal(data.getBytes());
        return encryptedText;
    }

    //Encrypts RSA public key with AES
    byte[] encryptKey(PublicKey publicKey, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //initialize cipher object to encrypt the key
        Cipher cipherKey = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherKey.init(Cipher.PUBLIC_KEY, publicKey);

        //encrypt key
        byte[] encryptedKey;
        encryptedKey = cipherKey.doFinal(secretKey.getEncoded());
        return encryptedKey;
    }

    //Decrypt encrypted message using private RSA key and encrypted public key
    String decrypt(byte[] encryptedText, byte[] encryptedKey, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //initialize cipher object to be ready to decrypt
        Cipher decryptKey= Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptKey.init(Cipher.PRIVATE_KEY, privateKey);

        //decrypt public key using secret key
        byte[] decryptedKey = decryptKey.doFinal(encryptedKey);
        SecretKey originalKey = new SecretKeySpec(decryptedKey,0, decryptedKey.length,"AES");
        Cipher keyCipher = Cipher.getInstance("AES");
        keyCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte [] decryptedText = keyCipher.doFinal(encryptedText);

        //return the decrypted text in string
        return new String(decryptedText,StandardCharsets.UTF_8);
    }

    //user signature
    String signature (String password, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //initialize signature
        Signature userSignature = Signature.getInstance("SHA256withRSA");
        userSignature.initSign(privateKey);
        userSignature.update(password.getBytes());
        byte [] signature = userSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }
    //Verify if the message is actually coming from the key holder
    boolean verify (String userText, String sign, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature publicSign = Signature.getInstance("SHA256withRSA");
        publicSign.initVerify(publicKey);
        publicSign.update(userText.getBytes());
        byte [] verifyByte = Base64.getDecoder().decode(sign);
        return publicSign.verify(verifyByte);
    }

}