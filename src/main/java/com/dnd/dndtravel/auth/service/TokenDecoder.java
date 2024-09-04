package com.dnd.dndtravel.auth.service;

import java.util.Base64;

import com.dnd.dndtravel.auth.exception.AppleTokenDecodingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenDecoder {
	/**
	 * 헤더, 페이로드, 서명 세 부분을 .으로 분리해 페이로드만 가져와서, Base64디코딩 하고 얻어온 바이트배열을
	 * String변환, JSON 변환을 거쳐 targetClass의 객체로 변환
	 * @param token
	 * @param targetClass
	 * @return
	 * @param <T>
	 */
	public static <T> T decodePayload(String token, Class<T> targetClass) {
		String[] tokenParts = token.split("\\.");
		String payloadJWT = tokenParts[1];
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(payloadJWT));
		ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			return objectMapper.readValue(payload, targetClass);
		} catch (Exception e) {
			throw new AppleTokenDecodingException(e);
		}
	}
}
