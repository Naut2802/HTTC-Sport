package com.fpoly.httc_sport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HttcSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttcSportApplication.class, args);
	}

}
