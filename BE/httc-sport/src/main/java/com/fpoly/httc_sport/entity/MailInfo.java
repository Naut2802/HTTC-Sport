package com.fpoly.httc_sport.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor @NoArgsConstructor
public class MailInfo {
	@Builder.Default
	String from = "maousama333@gmail.com";
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
}

