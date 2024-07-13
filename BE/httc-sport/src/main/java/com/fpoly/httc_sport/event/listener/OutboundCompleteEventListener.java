package com.fpoly.httc_sport.event.listener;

import com.fpoly.httc_sport.entity.MailInfo;
import com.fpoly.httc_sport.event.OutboundCompleteEvent;
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
public class OutboundCompleteEventListener implements ApplicationListener<OutboundCompleteEvent> {
	MailerService mailerService;
	
	@Override
	public void onApplicationEvent(OutboundCompleteEvent event) {
		var user = event.getUser();
		
		MailInfo mailInfo = MailInfo.builder()
				.from("maousama333@gmail.com")
				.to(user.getEmail())
				.subject("Email đăng ký tài khoản thông qua gmail")
				.body(mailerService.generateCreatePasswordBody(event.getPassword()))
				.build();
		
		try {
			mailerService.send(mailInfo);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
