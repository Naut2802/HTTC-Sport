package com.fpoly.httc_sport.event.listener;

import com.fpoly.httc_sport.entity.MailInfo;
import com.fpoly.httc_sport.entity.User;
import com.fpoly.httc_sport.event.RegistrationCompleteEvent;
import com.fpoly.httc_sport.service.AuthenticationService;
import com.fpoly.httc_sport.service.MailerService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
	AuthenticationService authenticationService;
	MailerService mailerService;
	
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		var user = event.getUser();
		String verificationToken = UUID.randomUUID().toString();
		authenticationService.saveVerificationToken(user, verificationToken);
		
		String url = event.getUrl()+"/verify-email?token="+verificationToken;
		
		MailInfo mailInfo = MailInfo.builder()
				.from("maousama333@gmail.com")
				.to(user.getEmail())
				.subject("Email xác thực tài khoản")
				.body(mailerService.generateVerificationBody(url))
				.build();
		
		try {
			mailerService.send(mailInfo);
			log.info(url);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
