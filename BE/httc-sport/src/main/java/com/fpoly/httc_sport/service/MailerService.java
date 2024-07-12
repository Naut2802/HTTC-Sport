package com.fpoly.httc_sport.service;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.MailInfo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.servlet.ServletContext;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailerService {
	JavaMailSender mailSender;
	@NonFinal
	List<MailInfo> list = new ArrayList<>();
	
	public void send(MailInfo mailInfo) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mailInfo.getFrom());
		helper.setTo(mailInfo.getTo());
		helper.setSubject(mailInfo.getSubject());
		helper.setText(mailInfo.getBody());
		helper.setReplyTo(mailInfo.getFrom());

		if(mailInfo.getCc() != null && mailInfo.getCc().length > 0) {
			helper.setCc(mailInfo.getCc());
		}
		if(mailInfo.getBcc() != null && mailInfo.getBcc().length > 0) {
			helper.setBcc(mailInfo.getBcc());
		}
		MimeBodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(mailInfo.getBody(), "text/html; charset=utf-8");
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(contentPart);
		message.setContent(multipart);
		
		mailSender.send(message);
	}
	
	public void queue(MailInfo mailInfo) {
		list.add(mailInfo);
	}
	
	public void queue(String to, String subject, String body, String cc, String bcc) {
		MailInfo mail = new MailInfo(to, subject, body);
		if(!cc.isEmpty() || cc.contains(",")) {
			mail.setCc(cc.split(","));
		}
		if(!bcc.isEmpty() || bcc.contains(",")) {
			mail.setBcc(bcc.split(","));
		}
		queue(mail);
	}
	
	public String generateVerificationBody(String url) {
		return "<p>Cảm ơn bạn đã đăng ký tài khoản của HTTC-Sport</p>"+
				"<p>Vui lòng nhấn vào đường link dưới đây để thực hiện kích hoạt tài khoản.</p>"+
				"<a href=\"" + url + "\">Xác thực email để kích hoạt tài khoản</a>"+
				"<p><br> Cảm ơn !";
	}
	
	public String generateForgotPasswordBody(String url) {
		return "<p>Bạn đã gửi yêu cầu reset mật khẩu</p>"+
				"<p>Vui lòng nhấn vào đường link dưới đây để thực hiện reset mật khẩu.</p>"+
				"<a href=\"" + url + "\">Click here</a>"+
				"<p><br> Cảm ơn !";
	}
	
	public String generateCreatePasswordBody(String password) {
		return "<p>Bạn đã gửi đăng ký tài khoản thông qua gmail</p>"+
				"<p>Đây là mật khẩu đăng nhập của bạn, vui lòng thay đổi lại mật khẩu và không chia sẻ cho bất cứ ai.</p>"+
				"<h1>\""+ password +"\"</h1>"+
				"<p><br> Cảm ơn !";
	}
	
	@Scheduled(fixedDelay = 1000)
	public void run() {
		while(!list.isEmpty()) {
			MailInfo mail = list.remove(0);
			try {
				this.send(mail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
