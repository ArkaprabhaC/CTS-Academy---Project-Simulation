package com.arka.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arka.customer.feign.CustomerErrorDecoder;

import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;

/**
 * Boostrap Spring MVC App
 * @author Arkaprabha
 *
 */
@SpringBootApplication
@EnableFeignClients
@NoArgsConstructor
public class AirbookCustomerApplication {
	

	/**
	 * Main method
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(AirbookCustomerApplication.class, args);
	}

	/**
	 * Error Decoder bean
	 * @return
	 */
	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomerErrorDecoder();
	}
	
	/**
	 * BCrypt password encoder bean
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
