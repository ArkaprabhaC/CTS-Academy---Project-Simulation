package com.cts.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.cts.admin.model.Admin;

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
	void addAuthUser(@RequestBody Admin admin);

	/**
	 * 
	 * @param customer
	 */
	@DeleteMapping("/delete")
	void deleteAuthUser(@RequestBody Admin admin);
	
	/**
	 * 
	 * @param customer
	 */
	@PutMapping("/update")
	void updateAuthUser(@RequestBody Admin admin);
	
}
