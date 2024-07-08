package com.fpoly.httc_sport.service;

import java.util.ArrayList;
import java.util.List;

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
public class MailerService {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	ServletContext application;
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
