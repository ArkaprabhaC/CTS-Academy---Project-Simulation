package com.cts.admin.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.admin.model.Admin;

/**
 * Admin Repository
 * @author Arkaprabha
 *
 */
public interface IAdminRepository extends JpaRepository<Admin, Long> {
	
	/**
	 * Finds user by username
	 * @param username
	 * @return Optional<Admin>
	 */
	Optional<Admin> findByUsername(final String username);
}
