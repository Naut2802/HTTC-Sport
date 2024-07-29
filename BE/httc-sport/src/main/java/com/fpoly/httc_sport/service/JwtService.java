package com.fpoly.httc_sport.service;

import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.security.jwt.KeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.StringJoiner;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtService {
	@Autowired
	KeyUtils keyUtils;
	@Value("${jwt.access-token-valid-duration}")
	long ACCESS_TOKEN_VALID_DURATION;
	@Value("${jwt.refresh-token-valid-duration}")
	long REFRESH_TOKEN_VALID_DURATION;
	
	public String generateAccessToken(User user, KeyPair keyPair) throws NoSuchAlgorithmException {
		log.info("[JwtGenerator::GenerateAccessToken] Token creation started for: {}", user.getUsername());
		
		String scopes = buildScope(user);
		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.subject(user.getId())
				.issuer("HTTC_Sport")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plus(ACCESS_TOKEN_VALID_DURATION, ChronoUnit.SECONDS))
				.claim("scope", scopes)
				.build();
		
		JwtEncoder jwtEncoder = buildJwtEncoder(keyUtils.getPublicKey(keyPair), keyUtils.getPrivateKey(keyPair));
		return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
	}
	
	public Jwt generateRefreshToken(User user, String jti, KeyPair keyPair) throws NoSuchAlgorithmException {
		log.info("[JwtGenerator::GenerateRefreshToken] Token creation started for: {}", user.getUsername());
		
		JwtEncoder jwtEncoder = buildJwtEncoder(keyUtils.getPublicKey(keyPair), keyUtils.getPrivateKey(keyPair));
		
		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.issuer("HTTC_Sport")
				.issuedAt(Instant.now())
				.id(jti)
				.expiresAt(Instant.now().plus(REFRESH_TOKEN_VALID_DURATION, ChronoUnit.SECONDS))
				.build();
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet));
	}
	
	private String buildScope(User user) {
		StringJoiner stringJoiner = new StringJoiner(" ");
		
		if (!CollectionUtils.isEmpty(user.getRoleSet())) {
			user.getRoleSet().forEach(role -> {
				stringJoiner.add("ROLE_" + role.getRoleName());
				// Xử lý permissions
//				if (!CollectionUtils.isEmpty(role.getPermissions())) {
//					role.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionName()));
//				}
			});
		}
		
		return stringJoiner.toString();
	}
	
	private JwtEncoder buildJwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
		JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
		JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwkSource);
	}
}
