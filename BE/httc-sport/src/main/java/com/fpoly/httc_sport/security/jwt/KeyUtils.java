package com.fpoly.httc_sport.security.jwt;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyUtils {
	public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		
		return keyPairGenerator.generateKeyPair();
	}
	
	public RSAPublicKey getPublicKey(KeyPair keyPair) {
		return (RSAPublicKey) keyPair.getPublic();
	}
	
	public RSAPrivateKey getPrivateKey(KeyPair keyPair) {
		return (RSAPrivateKey) keyPair.getPrivate();
	}
	
	public RSAPublicKey exchangeRSAPublicKey(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] parts = keyString.split("\\.");
		byte[] modulusBytes = Base64.getDecoder().decode(parts[0]);
		byte[] exponentBytes = Base64.getDecoder().decode(parts[1]);
		
		BigInteger modulus = new BigInteger(1, modulusBytes);
		BigInteger exponent = new BigInteger(1, exponentBytes);
		
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}
	
	public String exchangeRSAPublicKeyToString(RSAPublicKey publicKey) {
		String modulusBase64 = Base64.getEncoder().encodeToString(publicKey.getModulus().toByteArray());
		String exponentBase64 = Base64.getEncoder().encodeToString(publicKey.getPublicExponent().toByteArray());
		
		return modulusBase64 + "." + exponentBase64;
	}
}
