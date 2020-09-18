package com.cts.gateway.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cts.gateway.model.AuthUser;

/**
 * User Repository
 * @author Arkaprabha
 *
 */
public interface IUserRepository extends JpaRepository<AuthUser, Long>{

	/**
	 * 
	 * @param username
	 * @return User
	 */
	Optional<AuthUser> findByUsername(final String username);
	
	/**
	 * 
	 * @param username
	 * @param password
	 */
	@Modifying
	@Transactional
	@Query(value = "delete from AuthUser u where u.username = ?1 and u.password= ?2")
	void deleteByUsernameAndPassword(final String username, final String password);
	
}
