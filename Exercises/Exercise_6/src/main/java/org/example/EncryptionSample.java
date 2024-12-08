package org.example;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class EncryptionSample {

    public static void main(String[] args) throws Exception {
        // Step 1: Symmtric Encryption & Decryption (AES-256)
        System.out.println("Symmetric Encryption and Decryption:");

        // Make AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // we are using AES 256
        SecretKey aesKey = keyGen.generateKey();

        // message Alice wants to send
        String message = "Hey Bob, this is Alice!";
        System.out.println("Original Msg: " + message);

        // Encrypting the msg
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12]; // IV is important for AES-GCM
        new SecureRandom().nextBytes(iv); // generate IV
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);
        byte[] encryptedMessage = aesCipher.doFinal(message.getBytes());
        System.out.println("Encrypted Msg: " + Base64.getEncoder().encodeToString(encryptedMessage));

        // Decrypting the msg
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);
        byte[] decryptedMessage = aesCipher.doFinal(encryptedMessage);
        System.out.println("Decrypted Msg: " + new String(decryptedMessage));
        System.out.println(); // just spacing

        // Step 2: Asymmtric Encryption & Decryption (RSA-2048)
        System.out.println("Asymmetric Encryption and Decryption:");

        // Make RSA key pairs for Alice & Bob
        KeyPairGenerator rsaGen = KeyPairGenerator.getInstance("RSA");
        rsaGen.initialize(2048); // RSA-2048 keys
        KeyPair aliceKeyPair = rsaGen.generateKeyPair();
        KeyPair bobKeyPair = rsaGen.generateKeyPair();

        // Alice encrypts with Bob's public key
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, bobKeyPair.getPublic());
        byte[] rsaEncryptedMessage = rsaCipher.doFinal(message.getBytes());
        System.out.println("Encrypted Msg (RSA): " + Base64.getEncoder().encodeToString(rsaEncryptedMessage));

        // Bob decrypts with his private key
        rsaCipher.init(Cipher.DECRYPT_MODE, bobKeyPair.getPrivate());
        byte[] rsaDecryptedMessage = rsaCipher.doFinal(rsaEncryptedMessage);
        System.out.println("Decrypted Msg (RSA): " + new String(rsaDecryptedMessage));
        System.out.println();

        // Step 3: Signing & Signature Validation
        System.out.println("Signing and Signature Validation:");

        // Alice signs the msg with her private key
        Signature rsaSign = Signature.getInstance("SHA256withRSA");
        rsaSign.initSign(aliceKeyPair.getPrivate());
        rsaSign.update(message.getBytes());
        byte[] signature = rsaSign.sign();
        System.out.println("Digital Signiture: " + Base64.getEncoder().encodeToString(signature));

        // Bob verifies the signature using Alice's public key
        rsaSign.initVerify(aliceKeyPair.getPublic());
        rsaSign.update(message.getBytes());
        boolean isVerified = rsaSign.verify(signature);
        System.out.println("Signature Verified: " + isVerified);
    }
}
