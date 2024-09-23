package com.dnd.dndtravel.map.controller.swagger;

import java.lang.reflect.Type;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 스웨거에서 Multipart 요청시 발생하는 "Content-Type 'application/octet-stream' is not supported" 문제를 해결하기위한 클래스
 * Postman같은 프로그램으로 보낼시 사진과 json객체에 대한 타입을 별도로 지정할 수 있지만, 스웨거는 파라미터별로 별도 타입을 지정할수 없었던것이 원인
 */
@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	protected boolean canWrite(MediaType mediaType) {
		return false;
	}
}
