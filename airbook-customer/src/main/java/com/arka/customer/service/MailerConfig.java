package com.arka.customer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Mailer Config class
 * @author Arkaprabha
 *
 */
@Component
@Data
public class MailerConfig {

	/**
	 * Autowires host settings from properties
	 */
	@Value("${spring.mail.host}")
	private String host;
	
	/**
	 * Autowires port settings from properties
	 */
	@Value("${spring.mail.port}")
	private int port;
	
	/**
	 * Autowires username settings from properties
	 */
	@Value("${spring.mail.username}")
	private String username;
	
	/**
	 * Autowires password settings from properties
	 */
	@Value("${spring.mail.password}")
	private String password;
}
