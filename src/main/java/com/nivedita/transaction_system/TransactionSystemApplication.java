package com.nivedita.transaction_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableAsync

public class TransactionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionSystemApplication.class, args);
	}
}
