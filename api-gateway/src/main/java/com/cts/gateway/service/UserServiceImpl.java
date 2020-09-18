package com.cts.gateway.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.gateway.dao.IUserRepository;
import com.cts.gateway.model.AuthUser;

import lombok.AllArgsConstructor;

/**
 * User Service Implementation
 * @author Arkaprabha
 *
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

	/**
	 * Autowired User Repository
	 */
	@Autowired
	private IUserRepository repository;
	
	
	
	@Override
	public void addUser(final AuthUser user) {
		repository.save(user);
	}

	
	@Override
	public void deleteUser(final AuthUser user) {
		repository.deleteByUsernameAndPassword(user.getUsername(), user.getPassword());
	}


	@Override
	public void updateUser(final AuthUser user) {
		final AuthUser oldUserAcc = repository.findByUsername(user.getUsername()).get();
		
		oldUserAcc.setActive(user.isActive());
		oldUserAcc.setPassword(user.getPassword());
		
		repository.save(oldUserAcc);

		
	}

	
}
