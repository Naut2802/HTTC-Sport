package com.fpoly.httc_sport.event.listener;

import com.fpoly.httc_sport.entity.MailInfo;
import com.fpoly.httc_sport.event.ForgotPasswordEvent;
import com.fpoly.httc_sport.service.MailerService;
import com.fpoly.httc_sport.service.UserService;
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
public class ForgotPasswordEventListener implements ApplicationListener<ForgotPasswordEvent> {
	UserService userService;
	MailerService mailerService;
	
	@Override
	public void onApplicationEvent(ForgotPasswordEvent event) {
		var user = event.getUser();
		String token = UUID.randomUUID().toString();
		
		userService.saveForgotPasswordToken(user, token);
		String url = event.getUrl()+"/verify-token?token="+token;
		
		MailInfo mailInfo = MailInfo.builder()
				.to(user.getEmail())
				.subject("Email reset mật khẩu")
				.body(mailerService.generateForgotPasswordBody(url))
				.build();
		
		mailerService.queue(mailInfo);
		log.info(url);
	}
}
