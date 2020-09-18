package com.cts.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.admin.exception.AdminErrorDecoder;

import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Arkaprabha
 *
 */
@SpringBootApplication
@EnableFeignClients
@NoArgsConstructor
public class AirbookAdminApplication {

	/**
	 * Application runner
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(AirbookAdminApplication.class, args);
	}
	
	/**
	 * Error decoder bean
	 * @return
	 */
	@Bean
	public ErrorDecoder errorDecoder() {
		return new AdminErrorDecoder();
	}
	
	/**
	 * Password Encoder from 
	 * spring security Crypto library
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
