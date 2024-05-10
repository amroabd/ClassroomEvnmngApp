package com.is.classroomevnmngapp.utils.crypto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class DigitalSignature {

	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA"; // Chosen signing algorithm

	public byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
		try {
			Signature signer = Signature.getInstance(SIGNATURE_ALGORITHM);
			signer.initSign(privateKey);
			signer.update(data);
			return signer.sign();
		} catch (Exception e) {
			throw new Exception("Signing failed", e);
		}
	}

	public boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
		try {
			Signature verifier = Signature.getInstance(SIGNATURE_ALGORITHM);
			verifier.initVerify(publicKey);
			verifier.update(data);
			return verifier.verify(signature);
		} catch (Exception e) {
			throw new Exception("Verification failed", e);
		}
	}
}
