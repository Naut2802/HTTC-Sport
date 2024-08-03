package com.fpoly.httc_sport.utils;

import com.fpoly.httc_sport.repository.InvalidatedTokenRepository;
import com.fpoly.httc_sport.repository.RefreshTokenRepository;
import com.fpoly.httc_sport.repository.RentInfoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Cronjob {
	InvalidatedTokenRepository invalidatedTokenRepository;
	RentInfoRepository rentInfoRepository;
	RefreshTokenRepository refreshTokenRepository;
	
	@Scheduled(cron = "0 0/30 * * * ?")
	public void checkInvalidatedAccessTokenExpiryTime() {
		Date date = Date.from(Instant.now());
		var tokens = invalidatedTokenRepository.findByExpiryTimeLessThan(date);
		
		log.info("========================================================");
		log.info("Started Cronjob: check and delete Invalidated Access Token");
		invalidatedTokenRepository.deleteAll(tokens);
		log.info("Deleted {} invalidated tokens from database", tokens.size());
	}
	@Scheduled(cron = "0 0 0 * * ?")
	public void checkRefreshTokenExpiryTime() {
		Date date = Date.from(Instant.now());
		var tokens = refreshTokenRepository.findByExpiryTimeLessThan(date);
		
		log.info("========================================================");
		log.info("Started Cronjob: check and delete expired Refresh Token");
		refreshTokenRepository.deleteAll(tokens);
		log.info("Deleted {} expired tokens from database", tokens.size());
	}
	@Scheduled(cron = "0 0 0 * * ?")
	public void checkInvalidRentInfo() {
		LocalDate localDate = LocalDate.now();
		var rentInfos = rentInfoRepository.findByRentedAtLessThanAndPaymentStatusFalse(localDate);
		
		log.info("========================================================");
		log.info("Started Cronjob: check and delete expired Rent infos");
		rentInfoRepository.deleteAll(rentInfos);
		log.info("Deleted {} expired Rent infos from database", rentInfos.size());
	}
}
