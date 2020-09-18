package com.cts.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

/**
 * WebSecurity Config
 * @author Arkaprabha
 *
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * User Details service
	 */
	@Autowired
	private UserDetailsService service;
	
	
	/**
	 * 
	 */
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
	}
	

	/**
	 * Authentication manager
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	
	/**
	 * Returns password hashing algorithm
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
}
