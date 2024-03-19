package com.fpoly.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MailInfo {
	private String from;
	private String to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private String body;
	
	public MailInfo(String to, String subject, String body) {
		this.from = "maousama333@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}

