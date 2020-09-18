package com.cts.admin.exception;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import com.cts.admin.model.ResponseFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

/**
 * Custom Error Decoder implementation
 * Handles lower level exceptions thrown through feign client
 * @author Arkaprabha
 *
 */
@NoArgsConstructor
public class AdminErrorDecoder implements ErrorDecoder{

	/**
	 *  ResponseFormat
	 */
	private transient ResponseFormat error;
	
	/**
	 * Slf4j Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminErrorDecoder.class);
	
	/**
	 * Default Error Decoder
	 */
	private final transient ErrorDecoder defaultDecoder = new Default();
	
	@Override
	public Exception decode(final String methodKey, final Response response) {
		
		Body body;
		try {

			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			body = response.body();
			error = mapper.readValue(body.asInputStream(), ResponseFormat.class);	
			body.close();
			
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
		} 
		
		Exception exception;
		switch (response.status()) {
			case 404:
				exception =  new GenericNotFoundException(error.getMessage());
				break;
				
			case 409:
				exception = new GenericAlreadyExistException(error.getMessage()); 
				break;
				
			default:
				exception = defaultDecoder.decode(methodKey, response);
				break;
		} 
			
		return exception;
				
	}
	
}
