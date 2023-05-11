package com.greedy.mingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MingleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MingleApplication.class, args);
	}

}
