package com.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SorteoApplication {

	//https://www.javachinna.com/spring-boot-angular-10-user-registration-oauth2-social-login-part-1/
	public static void main(String[] args) {
		SpringApplication.run(SorteoApplication.class, args);
	}

}
