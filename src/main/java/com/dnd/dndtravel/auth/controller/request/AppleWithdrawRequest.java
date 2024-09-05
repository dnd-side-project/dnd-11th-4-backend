package com.dnd.dndtravel.auth.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
클라이언트에서 서버로 보내는 요청
 */
public record AppleWithdrawRequest(
        @NotBlank(message = "인증 코드가 존재하지 않습니다.")
        @Size(max = 300, message = "인증 코드 길이를 초과하였습니다.")
        String authorizationCode
) {
}
