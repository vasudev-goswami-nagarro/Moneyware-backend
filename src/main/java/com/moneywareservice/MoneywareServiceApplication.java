package com.moneywareservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneywareServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneywareServiceApplication.class, args);
	}

}
