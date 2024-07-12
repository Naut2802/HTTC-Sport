package com.fpoly.httc_sport.event;

import com.fpoly.httc_sport.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ForgotPasswordEvent extends ApplicationEvent {
	User user;
	String url;
	
	public ForgotPasswordEvent(User user, String url) {
		super(user);
		this.user = user;
		this.url = url;
	}
}
