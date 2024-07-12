package com.fpoly.httc_sport.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
public class MailInfo {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	
	public MailInfo(String to, String subject, String body) {
		this.from = "maousama333@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}

