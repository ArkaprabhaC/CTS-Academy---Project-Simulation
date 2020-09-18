package com.cts.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import lombok.NoArgsConstructor;

/**
 * Resource server for the application
 * @author Arkaprabha
 *
 */
@Configuration
@EnableResourceServer
@NoArgsConstructor
public class OAuthResourceServer extends ResourceServerConfigurerAdapter{

	/**
	 * Resource Server Config
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/airbook-customer/api/v1/customer/add").not().hasRole("USER")
			.antMatchers("/airbook-customer/api/v1/customer/{id}").hasRole("USER")
			.antMatchers("/airbook-customer/api/v1/customer/delete/**").hasRole("USER")
			.antMatchers("/airbook-customer/api/v1/customer/update/**").hasRole("USER")
			.antMatchers("/airbook-customer/api/v1/customer/airport/**").hasRole("USER")
			.antMatchers("/airbook-customer/api/v1/customer/flight/**").hasRole("USER")
			.antMatchers("/airbook-customer/api/v1/customer/book/**").hasRole("USER")
			
			.antMatchers("/airbook-admin/api/v1/admin/add").hasRole("ROOT_ADMIN")
			.antMatchers("/airbook-admin/api/v1/admin/delete/{username}").hasRole("ROOT_ADMIN")
			.antMatchers("/airbook-admin/api/v1/admin/all").hasRole("ROOT_ADMIN")	
			
			.antMatchers("/airbook-admin/api/v1/admin/add/flight").hasRole("ADMIN")
			.antMatchers("/airbook-admin/api/v1/admin/delete/flight/{planeNo}").hasRole("ADMIN")
			.antMatchers("/airbook-admin/api/v1/admin/add/airport").hasRole("ADMIN")
			.antMatchers("/airbook-admin/api/v1/admin/delete/airport/{icao}").hasRole("ADMIN")	
			.antMatchers("/airbook-admin/api/v1/admin/{username}").hasRole("ADMIN")		
			 
			.antMatchers("/oauth/**").permitAll()
			.antMatchers("/airbook-airports/**").denyAll()
			.antMatchers("/airbook-flights/**").denyAll()
			.antMatchers("/airbook-booking/**").denyAll()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	
}
