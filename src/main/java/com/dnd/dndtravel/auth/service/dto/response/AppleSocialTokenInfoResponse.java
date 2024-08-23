package com.dnd.dndtravel.auth.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @param accessToken
 * @param tokenType: 액세스 토큰은 항상 Bearer
 * @param expiresIn
 * @param refreshToken
 * @param idToken: 사용자 신원정보가 포함된 JWT
 */
public record AppleSocialTokenInfoResponse(
	@JsonProperty("access_token")
	String accessToken,
	@JsonProperty("token_type")
	String tokenType,
	@JsonProperty("expires_in")
	Long expiresIn,
	@JsonProperty("refresh_token")
	String refreshToken,
	@JsonProperty("id_token")
	String idToken
) {
}
