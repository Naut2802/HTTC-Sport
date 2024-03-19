package com.fpoly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HTTCSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(HTTCSportApplication.class, args);
	}

}
