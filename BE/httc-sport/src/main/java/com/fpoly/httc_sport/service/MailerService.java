package com.fpoly.httc_sport.service;

import java.util.ArrayList;
import java.util.List;

import com.fpoly.httc_sport.entity.RentInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fpoly.httc_sport.entity.MailInfo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailerService {
	JavaMailSender mailSender;
	@NonFinal
	List<MailInfo> list = new ArrayList<>();
	
	@NonFinal
	@Value("${spring.security.cors.cross.origin}")
	String CROSS_ORIGIN;
	
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
	
	public String generateRentPitchBody(RentInfo rentInfo) {
		return "<p><strong>Th&ocirc;ng tin đặt s&acirc;n của Kh&aacute;ch h&agrave;ng:&nbsp;</strong></p>\r\n"
				+ "<p>Email: <strong>" + rentInfo.getEmail() + "</strong></p>\r\n"
				+ "<p>SĐT: <strong>" + rentInfo.getPhoneNumber() + "</strong></p>\r\n"
				+ "<p>T&ecirc;n s&acirc;n: <strong>" + rentInfo.getPitch().getPitchName() + "</strong></p>\r\n"
				+ "<p>Địa chỉ s&acirc;n b&oacute;ng: <strong>" + rentInfo.getPitch().getAddress().getStreet()
				+ " " + rentInfo.getPitch().getAddress().getWard() + ", " + rentInfo.getPitch().getAddress().getDistrict()
				+ ", " + rentInfo.getPitch().getAddress().getCity()
				+ "</strong></p>\r\n"
				+ "<p>Ng&agrave;y đặt: <strong>" + rentInfo.getRentedAt() + "</strong></p>\r\n"
				+ "<p>Thời gian bắt đầu: <strong>" + rentInfo.getStartTime() + "</strong></p>\r\n"
				+ "<p>Thời gian kết th&uacute;c: <strong>" + rentInfo.getEndTime()
				+ "</strong></p>\r\n"
				+ "<p>&nbsp;</p>\r\n"
				+ "<p>Để xem được th&ocirc;ng tin đặt s&acirc;n cụ thể: <a href=\"" + CROSS_ORIGIN + "/user/thong-tin-dat-san/" + rentInfo.getId() + "\">Nhấn v&agrave;o đ&acirc;y</a>.</p>"
				+ "<h4> Cảm ơn đã đặt sân của chúng tôi, chúc quý khách có một trải nghiệm thật tốt !";
	}
	
	public String generateDepositBody(String email, String username, int paymentAmount, int total) {
		return "<h2>Thực hiện thanh toán nạp tiền vào ví HTTC-Wallet thành công</h2>"
				+ "<h4>Đây là thông tin giao dịch</h4>"
				+ "<p>Email: <strong>" + email + "</strong></p>\r\n"
				+ "<p>Tên tài khoản: <strong>" + username + "</strong></p>\r\n"
				+ "<p>Số tiền giao dịch: <strong>" + paymentAmount + "</strong></p>\r\n"
				+ "<p>Số dư trong ví hiện tại: <strong>" + total + "</strong></p>\r\n"
				+ "<p><br> Cảm ơn đã sử dụng dịch vụ của HTTC-Sport !";
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
