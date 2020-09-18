package com.cts.airports.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Airport model
 * @author Arkaprabha
 *
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long airportId;
	
	/**
	 * IATA code
	 */
	@JsonProperty("IATA_code") 
    private String iata;
    
	/**
	 * ICAO code
	 */
	@JsonProperty("ICAO_code") 
    private String icao;
    
	/**
	 * Airport name
	 */
    @JsonProperty("airport_name")
    private String name;
    
    /**
     * City name
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
