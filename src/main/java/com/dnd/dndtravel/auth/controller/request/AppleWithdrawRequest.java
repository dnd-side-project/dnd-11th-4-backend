package com.dnd.dndtravel.auth.controller.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
클라이언트에서 서버로 보내는 요청
 */
public record AppleWithdrawRequest(
        @Schema(description = "authorization code", requiredMode = REQUIRED)
        @NotBlank(message = "authorization code는 필수 입니다.")
        @Size(max = 300, message = "authorization code 형식이 아닙니다.")
        String authorizationCode
) {
}
