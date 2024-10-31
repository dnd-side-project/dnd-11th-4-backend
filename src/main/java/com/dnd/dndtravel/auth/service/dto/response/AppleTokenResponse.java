package com.dnd.dndtravel.auth.service.dto.response;

public record AppleTokenResponse(
	String sub,
	String name,
	String email,
	String appleRefreshToken
) {
	public static AppleTokenResponse of(AppleIdTokenPayload appleIdTokenPayload, String appleRefreshToken) {
		return new AppleTokenResponse(
			appleIdTokenPayload.sub(),
			appleIdTokenPayload.name(),
			appleIdTokenPayload.email(),
			appleRefreshToken
		);
	}
}
