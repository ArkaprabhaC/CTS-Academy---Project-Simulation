package com.arka.customer.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arka.customer.model.Airport;



/**
 * Feign Client to get details from Airport service
 * @author Arkaprabha
 *
 */
@FeignClient(name = "airbook-airports")
public interface AirportClient {

	/**
	 * Returns all airport
	 * @return List<Airport>
	 */
	@GetMapping("/api/v1/airport")
	List<Airport> getAllAirports();
	
	
	/**
	 * Gets Airport By name
	 * @param name
	 * @return Airport
	 */
	@GetMapping("/api/v1/airport/name/{name}")
	Airport getAirportByName(@PathVariable final String name);
	
	
	/**
	 * Gets airport by ICAO code
	 * @param icao
	 * @return Airport
	 */
	@GetMapping("/api/v1/airport/icao/{icao}")
	Airport getAirportByICAO(@PathVariable final String icao);
	
}
