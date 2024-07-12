package com.fpoly.httc_sport.event;

import com.fpoly.httc_sport.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutboundCompleteEvent extends ApplicationEvent {
	User user;
	String password;
	
	public OutboundCompleteEvent(User user, String password) {
		super(user);
		this.user = user;
		this.password = password;
	}
}
