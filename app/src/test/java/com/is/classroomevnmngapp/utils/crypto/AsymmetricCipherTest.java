package com.is.classroomevnmngapp.utils.crypto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AsymmetricCipherTest {



    private AsymmetricCipher asymmetricCipher;
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Before
    void setUp() throws Exception {
        asymmetricCipher = new AsymmetricCipher();
        keyPair = asymmetricCipher.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    @Test
    void testEncryptDecrypt() {
        try {
            String originalData = "Hello, this text original before encrypt.!";
            byte[] encryptedData = asymmetricCipher.encrypt(originalData.getBytes(), publicKey);
            byte[] decryptedData = asymmetricCipher.decrypt(encryptedData, privateKey);
            String decryptedString = new String(decryptedData);

         assertEquals(originalData, decryptedString);
        } catch (Exception e) {

            e.printStackTrace();
           fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testKeyLoading() {
        try {
            String encodedPublicKey = publicKey.getAlgorithm();
            String encodedPrivateKey =privateKey.getAlgorithm();

            PublicKey loadedPublicKey = asymmetricCipher.loadPublicKey(encodedPublicKey);
            PrivateKey loadedPrivateKey = asymmetricCipher.loadPrivateKey(encodedPrivateKey);

            assertEquals(publicKey, loadedPublicKey);
           assertEquals(privateKey, loadedPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
           fail("Exception occurred: " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
    }
}