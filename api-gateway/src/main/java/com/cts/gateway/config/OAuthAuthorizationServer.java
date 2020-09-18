package com.cts.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * Centralized Authorization Server for the Application
 * 
 * @author Arkaprabha
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuthAuthorizationServer extends AuthorizationServerConfigurerAdapter{
	
	/**
	 * Authentication manager provided by Spring Security
	 */
	@Autowired
	private transient AuthenticationManager authManager;
	
	/**
	 * Autowired passwordEncoder bean
	 */
	@Autowired
	private transient PasswordEncoder passwordEncoder;


	/**
	 * Used to convert Jwt to object and vice versa
	 * @return JwtAccessTokenConverter
	 */
	@Bean
	public JwtAccessTokenConverter tokenConverter() {
		return new JwtAccessTokenConverter();
	}
	
	/**
	 * 
	 * @return TokenStore
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(tokenConverter());
	}
	
	/**
	 * Configures security
	 */
	@Override
	public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}

	
	/**
	 * 
	 */
	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("web-client")
				.secret(passwordEncoder.encode("web-client-secret"))
				.scopes("resource_read","resource_write")
				.authorizedGrantTypes("password")
				.resourceIds("oauth2-resource");
	}

	/**
	 * 
	 */
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.authenticationManager(authManager)
					.tokenStore(tokenStore()).tokenEnhancer(tokenConverter());
		
	}
	
	
	

	
}
