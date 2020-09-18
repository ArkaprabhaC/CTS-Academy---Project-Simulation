package com.arka.customer.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Airport model
 * @author Arkaprabha
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

	/**
	 * airportID
	 */
	private long airportId;
	
	/**
	 * IATA
	 */
	@JsonProperty("IATA_code") 
    private String iata;
    
	/**
	 * ICAO 
	 */
	@JsonProperty("ICAO_code") 
    private String icao;
    
	/**
	 * Airport name
	 */
    @JsonProperty("airport_name")
    private String name;
    
    /**
     * City Name
     */
    @JsonProperty("city_name")
    private String city;

    /**
     * 
     * @param iata
     * @param icao
     * @param name
     * @param city
     */
	public Airport(final String iata, final String icao, final String name, final String city) {
		super();
		this.iata = iata;
		this.icao = icao;
		this.name = name;
		this.city = city;
	}
    
    
    
}
