package com.dnd.dndtravel.auth.service.dto.response;

public record TokenResponse(
	String accessToken,
	String refreshToken,
	String appleRefreshToken
) {
}