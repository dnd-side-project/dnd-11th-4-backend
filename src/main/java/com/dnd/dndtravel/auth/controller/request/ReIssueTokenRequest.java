package com.dnd.dndtravel.auth.controller.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReIssueTokenRequest(
	@Schema(description = "refresh token", requiredMode = REQUIRED)
	@NotBlank(message = "refresh token은 필수 입니다.")
	@Size(max = 300, message = "refresh token 형식이 아닙니다.")
	String refreshToken
) {
}
