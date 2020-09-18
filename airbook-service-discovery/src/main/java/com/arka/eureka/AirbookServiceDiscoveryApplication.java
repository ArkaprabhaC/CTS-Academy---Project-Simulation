package com.arka.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * @author Arkaprabha
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class AirbookServiceDiscoveryApplication {

	/**
	 * Application Runner
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(AirbookServiceDiscoveryApplication.class, args);
	}

}
