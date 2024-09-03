package com.dnd.dndtravel.auth.controller.swagger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.dnd.dndtravel.auth.controller.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.controller.request.ReIssueTokenRequest;
import com.dnd.dndtravel.auth.service.dto.response.ReissueTokenResponse;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "auth", description = "인증 API")
public interface AuthControllerSwagger {

	String STATUS_CODE_400_BODY_MESSAGE = "{\"message\":\"잘못된 요청입니다\"}";

	@Operation(
		summary = "애플 OAuth login API"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 로그인",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "204", description = "refresh token 재발급 필요시",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(
			responseCode = "500", description = "서버 오류",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = "{\"message\":\"Internal Server Error\"}")
			)
		)
	})
	ResponseEntity<TokenResponse> appleOAuthLogin(
		@Parameter(description = "로그인 요청 정보(authorization code, color)", required = true)
		AppleLoginRequest appleLoginRequest
	);

	@Operation(
		summary = "Refresh Token 재발급 API"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 발급",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ReissueTokenResponse.class))),
		@ApiResponse(responseCode = "400", description = "유효하지 않은 RefreshToken 요청시",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
		),
		@ApiResponse(
			responseCode = "500", description = "서버 오류",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = "{\"message\":\"Internal Server Error\"}")
			)
		)
	})
	ReissueTokenResponse reissueToken(
		@Parameter(description = "refreshToken", required = true)
		ReIssueTokenRequest reissueTokenRequest
	);
}
