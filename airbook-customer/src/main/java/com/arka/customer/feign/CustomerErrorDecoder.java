package com.arka.customer.feign;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arka.customer.exception.GenericAlreadyExistException;
import com.arka.customer.exception.GenericNotFoundException;
import com.arka.customer.model.ResponseFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;

/**
 * CustomErrorDecoder implementation of ErrorDecoder
 * @author Arkaprabha
 *
 */
@NoArgsConstructor
public class CustomerErrorDecoder implements ErrorDecoder{

	/**
	 * default Decoder
	 */
	private final transient ErrorDecoder defaultDecoder = new Default();
	
	/**
	 * Responseformat object
	 */
	private transient ResponseFormat error;
	
	/**
	 * Slf4j Logger
	 */
	private static final transient Logger LOGGER = LoggerFactory.getLogger(CustomerErrorDecoder.class);
	
	@Override
	public Exception decode(final String methodKey, final Response response) {
		
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			error = mapper.readValue(response.body().asInputStream(), ResponseFormat.class);
			
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
