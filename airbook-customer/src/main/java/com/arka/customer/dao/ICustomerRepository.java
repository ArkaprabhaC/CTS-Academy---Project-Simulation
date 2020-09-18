package com.arka.customer.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arka.customer.model.Customer;

/**
 * Interface used to interact with database objects
 * Implementation is provided at runtime by hibernate ORM
 * @author Arkaprabha Chatterjee
 *
 */
public interface ICustomerRepository extends JpaRepository<Customer, Long>{

	/**
	 * 
	 * @param email
	 * @return Customer
	 */
	Optional<Customer> findByEmail(final String email);
	
	/**
	 * 
	 * @param username
	 * @return Customer
 	 */
	Optional<Customer> findCustomerByUsername(final String username);
}
