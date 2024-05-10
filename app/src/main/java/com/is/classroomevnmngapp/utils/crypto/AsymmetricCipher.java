package com.is.classroomevnmngapp.utils.crypto;


import android.annotation.SuppressLint;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

@SuppressLint({"NewApi", "LocalSuppress"})
public class AsymmetricCipher {

    private static final int KEY_SIZE = 2048; // Adjust key size as needed (1024, 2048, 4096)

    public KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(KEY_SIZE);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new Exception("Key generation failed", e);
        }
    }

    public byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1Padding"); // Chosen algorithm and padding
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception("Encryption failed", e);
        }
    }

    public byte[] decrypt(byte[] ciphertext, PrivateKey privateKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1Padding"); // Matches encryption settings
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new Exception("Decryption failed", e);
        }
    }

    // Methods for loading/saving keys from/to files (optional)

    public PublicKey loadPublicKey(String encodedPublicKey) throws Exception {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(encodedPublicKey);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new Exception("Loading public key failed", e);
        }
    }

    public PrivateKey loadPrivateKey(String encodedPrivateKey) throws Exception {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(encodedPrivateKey);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            throw new Exception("Loading private key failed", e);
        }
    }
}
