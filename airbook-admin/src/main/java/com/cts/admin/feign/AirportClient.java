package com.cts.admin.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.admin.model.Airport;
import com.cts.admin.model.ResponseFormat;




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
	
	
	
	
	/**
	 * Add Airport to the system
	 * @param airport
	 * @return
	 */
	@PostMapping("/api/v1/airport/add")
	ResponseFormat addAirport(@RequestBody final Airport airport);
	
	/**
	 * Deletes an airport from the system
	 * @param icao
	 * @return
	 */
	@DeleteMapping("/api/v1/airport/delete/{icao}")
	ResponseFormat deleteAirport(@PathVariable final String icao);
	
}
