package com.cts.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.gateway.model.AuthUser;
import com.cts.gateway.service.UserService;

import lombok.AllArgsConstructor;

/**
 * DBSync Controller
 * @author Arkaprabha
 *
 */
@RestController
@AllArgsConstructor
public class DBSyncController {

	/**
	 * 
	 */
	@Autowired
	private UserService service;
	
	
	/**
	 * Add user to auth system
	 * Only to be used for syncing DB
	 * @param user
	 */
	@PostMapping("/add")
	public void addUser(@RequestBody final AuthUser user) {
		service.addUser(user);
	}
	
	/**
	 * Delete user to auth system
	 * Only to be used for syncing DB
	 * @param user
	 */
	@DeleteMapping("/delete")
	public void deleteUser(@RequestBody final AuthUser user) {
		service.deleteUser(user);
	}
	
	/**
	 * Updates user in the auth system
	 * Only to be used for syncing DB
	 * @param user
	 */
	@PutMapping("/update")
	public void updateUser(@RequestBody final AuthUser user) {
		service.updateUser(user);
	}
	
	
}
