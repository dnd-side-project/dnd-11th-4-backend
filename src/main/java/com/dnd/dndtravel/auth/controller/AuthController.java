package com.dnd.dndtravel.auth.controller;

import com.dnd.dndtravel.auth.controller.request.ReIssueTokenRequest;
import com.dnd.dndtravel.auth.controller.swagger.AuthControllerSwagger;
import com.dnd.dndtravel.auth.service.dto.response.AppleIdTokenPayload;
import com.dnd.dndtravel.auth.service.AppleOAuthService;
import com.dnd.dndtravel.auth.service.JwtTokenService;
import com.dnd.dndtravel.auth.controller.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.auth.service.dto.response.ReissueTokenResponse;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerSwagger {
    private final AppleOAuthService appleOAuthService;
    private final JwtTokenService jwtTokenService;
    private final MemberService memberService;

    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOAuthLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        // 클라이언트에서 준 code 값으로 apple의 IdToken Payload를 얻어온다
        AppleIdTokenPayload tokenPayload = appleOAuthService.get(appleLoginRequest.appleToken());

        // apple에서 가져온 유저정보를 DB에 저장
        Member member = memberService.saveMember(tokenPayload.email(), appleLoginRequest.selectedColor());

        // 클라이언트와 주고받을 user token(access , refresh) 생성
        TokenResponse tokenResponse = jwtTokenService.generateTokens(member.getId());

        // refresh token 재발급 필요시
        if (tokenResponse == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/reissue/token")
    public ReissueTokenResponse reissueToken(@RequestBody ReIssueTokenRequest reissueTokenRequest) {
        return jwtTokenService.reIssue(reissueTokenRequest.refreshToken());
    }
}