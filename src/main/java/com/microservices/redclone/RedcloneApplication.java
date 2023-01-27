package com.microservices.redclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedcloneApplication.class, args);
	}

}
