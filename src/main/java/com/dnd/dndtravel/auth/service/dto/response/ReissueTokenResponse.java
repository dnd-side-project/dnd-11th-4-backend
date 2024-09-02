package com.dnd.dndtravel.auth.service.dto.response;

public record ReissueTokenResponse(
	String accessToken,
	String refreshToken
) {
}
