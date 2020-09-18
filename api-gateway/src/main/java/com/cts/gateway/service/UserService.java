package com.cts.gateway.service;

import com.cts.gateway.model.AuthUser;

/**
 * 
 * @author Arkaprabha
 *
 */
public interface UserService {

	/**
	 * Adds user to DB
	 * @param user
	 */
	void addUser(AuthUser user);
	
	/**
	 * Deletes user from DB
	 * @param user
	 */
	void deleteUser(AuthUser user);

	/**
	 * Updates user in DB
	 * @param user
	 */
	void updateUser(AuthUser user);
	
}
