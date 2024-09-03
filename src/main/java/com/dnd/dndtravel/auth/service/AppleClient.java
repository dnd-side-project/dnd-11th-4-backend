package com.dnd.dndtravel.auth.service;

import com.dnd.dndtravel.auth.controller.request.AppleRevokeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dnd.dndtravel.auth.service.dto.response.AppleSocialTokenInfoResponse;
import com.dnd.dndtravel.auth.config.AppleFeignClientConfiguration;

@FeignClient(
        name = "apple-auth",
        url = "https://appleid.apple.com",
        configuration = AppleFeignClientConfiguration.class
)
public interface AppleClient {
    /**
     * @param clientId(required)     : 맵땅의 식별자값
     * @param clientSecret(required) : 개발자가 만든 비밀 JWT 토큰, 개발자 계정과 비밀키로 애플에 로그인할때 사용된다.
     * @param grantType(required)    : refresh token과 authorization code 를 검증하기위해 사용됨, 우린 현재 authorization code 사용중
     * @param code                   : 애플에게 받은 오직 5분만 유효한 일회용 인증코드,  authorization code 검증 용도로 필요하다.
     * @return
     */
    @PostMapping("/auth/token")
    AppleSocialTokenInfoResponse getIdToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code
    );

    @PostMapping("/auth/revoke")
    AppleRevokeRequest revoke(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("token") String refreshToken,
            @RequestParam("token_type_hint") String tokenType
    );
}
