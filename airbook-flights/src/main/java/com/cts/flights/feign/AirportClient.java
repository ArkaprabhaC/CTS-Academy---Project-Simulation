package com.cts.flights.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Airport feign client
 * @author Arkaprabha
 *
 */
@FeignClient(name = "airbook-airports")
public interface AirportClient {

	/**
	 * checks airport by ICAO
	 * if ICAO does not exist, it throws AirportNotFoundException
	 * 
	 * @param icao
	 */
	@GetMapping("/api/v1/airport/icao/{icao}")
	void checkIcao(@PathVariable final String icao);
}
