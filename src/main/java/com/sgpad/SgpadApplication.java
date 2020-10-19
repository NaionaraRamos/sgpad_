package com.sgpad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SgpadApplication {	
	public static void main(String[] args) {
		SpringApplication.run(SgpadApplication.class, args);
	}
}