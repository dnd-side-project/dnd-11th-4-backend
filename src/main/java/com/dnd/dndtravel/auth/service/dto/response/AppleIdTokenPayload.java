package com.dnd.dndtravel.auth.service.dto.response;

/**
 * apple의 idtoken을 까서 나오는 값들
 * @param sub: 사용자 고유 식별자(subject)
 * @param name
 * @param email
 */
public record AppleIdTokenPayload(
	String sub,
	String name,
	String email
) {
}
