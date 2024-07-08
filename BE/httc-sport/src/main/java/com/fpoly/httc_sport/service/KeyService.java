package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.exception.AppException;
import com.fpoly.httc_sport.exception.ErrorCode;
import com.fpoly.httc_sport.repository.RefreshTokenRepository;
import com.fpoly.httc_sport.security.jwt.KeyUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeyService {
	KeyUtils keyUtils;
	RefreshTokenRepository refreshTokenRepository;
	
	public RSAPublicKey getPublicKey(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return keyUtils.exchangeRSAPublicKey(keyString);
	}
	
	public String getPublicKeyFromDb(String id) {
		return refreshTokenRepository.findById(id).orElseThrow(() ->
						new AppException(ErrorCode.UNAUTHENTICATED))
				.getPublicKey();
	}
}
