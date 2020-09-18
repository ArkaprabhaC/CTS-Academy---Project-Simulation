package com.arka.customer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.arka.customer.model.Customer;

/**
 * Feign client for Auth and Customer Database sync
 * @author Arkaprabha
 *
 */
@FeignClient(name = "api-gateway")
public interface DBSyncClient {
	
	/**
	 * 
	 * @param customer
	 */
	@PostMapping("/add")
	void addAuthUser(@RequestBody Customer customer);

	/**
	 * 
	 * @param customer
	 */
	@DeleteMapping("/delete")
	void deleteAuthUser(@RequestBody Customer customer);
	
	/**
	 * 
	 * @param customer
	 */
	@PutMapping("/update")
	void updateAuthUser(@RequestBody Customer customer);
	
}
