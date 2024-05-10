package com.is.classroomevnmngapp.utils.crypto;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHash {

    public byte[] hash(byte[] data) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Chosen hashing algorithm
            digest.update(data);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("Hashing failed (algorithm not found)", e);
        }
    }
}

