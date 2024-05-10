package com.is.classroomevnmngapp.utils.crypto;

import androidx.annotation.NonNull;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricCipher {

    private static final int KEY_LENGTH = 256; // Adjust key length as needed (128, 192, 256)
    private static final int ITERATIONS = 65536; // Adjust iterations for key derivation strength
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; // Chosen algorithm and mode

    public byte[] encrypt(char[] password, byte[] data) throws CryptoException {
        try {
            // Generate a random initialization vector (IV) for each encryption
            byte[] iv = new byte[Cipher.getInstance(CIPHER_ALGORITHM).getBlockSize()];
            new SecureRandom().nextBytes(iv);

            // Derive a secure key from the password using PBKDF2
            byte[] salt = new byte[32]; // Adjust salt size as needed
            new SecureRandom().nextBytes(salt);
            SecretKeySpec key = generateKey(password, salt);

            // Create a Cipher instance
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

            // Initialize Cipher for encryption with the derived key and IV
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

            // Encrypt the data
            byte[] encryptedData = cipher.doFinal(data);

            // Prepend the IV to the encrypted data for decryption
            byte[] combinedData = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, combinedData, 0, iv.length);
            System.arraycopy(encryptedData, 0, combinedData, iv.length, encryptedData.length);

            return combinedData;
        } catch (Exception e) {
            throw new CryptoException("Encryption failed", e);
        }
    }

    public byte[] decrypt(char[] password, byte[] encryptedData) throws CryptoException {
        try {
            // Extract the IV from the beginning of the encrypted data
            byte[] iv = Arrays.copyOfRange(encryptedData, 0, Cipher.getInstance(CIPHER_ALGORITHM).getBlockSize());
            byte[] ciphertext = Arrays.copyOfRange(encryptedData, iv.length, encryptedData.length);

            // Derive a secure key from the password using PBKDF2 (same salt used during
            // encryption)
            byte[] salt = Arrays.copyOfRange(encryptedData, iv.length, iv.length + 32); // assuming salt is prepended
            SecretKeySpec key = generateKey(password, salt);

            // Create a Cipher instance
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

            // Initialize Cipher for decryption with the derived key and IV
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            // Decrypt the data
            return cipher.doFinal(ciphertext);// Base64.getDecoder().decode(ciphertext);

        } catch (Exception e) {
            throw new CryptoException("Decryption failed", e);
        }
    }

    @NonNull
    private SecretKeySpec generateKey(char[] password, byte[] salt) throws Exception {
        // Derive a secure key from the password and salt using PBKDF2
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
