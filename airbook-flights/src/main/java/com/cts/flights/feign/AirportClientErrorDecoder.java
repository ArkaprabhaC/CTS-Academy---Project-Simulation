package com.cts.flights.feign;

import com.cts.flights.exception.AirportNotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;

/**
 * AirportClient Error Decoder
 * @author Arkaprabha
 *
 */
@NoArgsConstructor
public class AirportClientErrorDecoder implements ErrorDecoder {

	/**
	 * default 
	 */
	private static ErrorDecoder defaultDecoder = new Default();
	
	/**
	 * NotFound Status code
	 */
	private static final int NOT_FOUND_CODE = 404;
	
	@Override
	public Exception decode(final String methodKey, final Response response) {
		Exception exception;
		
		if (response.status() == NOT_FOUND_CODE) {
			   exception = new AirportNotFoundException();
		}else {
			   exception = defaultDecoder.decode(methodKey, response);
		}
		
		
		return exception;
	}

}
