package com.cts.flights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.cts.flights.feign.AirportClientErrorDecoder;

import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Arkaprabha
 *
 */
@SpringBootApplication
@EnableFeignClients
@NoArgsConstructor
public class AirbookFlightsApplication {

	/**
	 * Application runner
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(AirbookFlightsApplication.class, args);
	}
	
	
	/**
	 * Error decoder bean
	 * @return
	 */
	@Bean
	public ErrorDecoder errorDecoder() {
		return new AirportClientErrorDecoder();
	}


}
