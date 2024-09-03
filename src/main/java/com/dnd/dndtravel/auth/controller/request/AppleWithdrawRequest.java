package com.dnd.dndtravel.auth.controller.request;

/*
클라이언트에서 서버로 보내는 요청
 */
public record AppleWithdrawRequest(
        String authorizationCode,
        long memberId
) {
}
