package com.dnd.dndtravel.auth.config;

import org.springframework.context.annotation.Bean;

import com.dnd.dndtravel.auth.service.AppleFeignClientErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppleFeignClientConfiguration {

	@Bean
	public AppleFeignClientErrorDecoder appleFeignClientErrorDecoder() {
		return new AppleFeignClientErrorDecoder(new ObjectMapper());
	}
}
