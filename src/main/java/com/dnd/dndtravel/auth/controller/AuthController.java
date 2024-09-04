package com.dnd.dndtravel.auth.controller;

import com.dnd.dndtravel.auth.controller.request.AppleWithdrawRequest;
import com.dnd.dndtravel.auth.controller.request.ReIssueTokenRequest;
import com.dnd.dndtravel.auth.service.dto.response.AppleIdTokenPayload;
import com.dnd.dndtravel.auth.service.AppleOAuthService;
import com.dnd.dndtravel.auth.service.JwtTokenService;
import com.dnd.dndtravel.auth.controller.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.auth.service.dto.response.ReissueTokenResponse;
import com.dnd.dndtravel.config.AuthenticationMember;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AppleOAuthService appleOAuthService;
    private final JwtTokenService jwtTokenService;
    private final MemberService memberService;

    //todo 클라이언트에서 실제 인증코드 보내주면 테스트 진행 필요
    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOAuthLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        // 클라이언트에서 준 code 값으로 apple의 IdToken Payload를 얻어온다
        AppleIdTokenPayload tokenPayload = appleOAuthService.get(appleLoginRequest.appleToken());

        // apple에서 가져온 유저정보를 DB에 저장
        Member member = memberService.saveMember(tokenPayload.name(), tokenPayload.email(), appleLoginRequest.selectedColor());

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

    @DeleteMapping("/withdraw")
    public void withdraw(@Valid @RequestBody AppleWithdrawRequest withdrawRequest, AuthenticationMember authenticationMember) {
        // 1. Apple 서버에서 Access Token 받아오기
        String accessToken = appleOAuthService.getAccessToken(withdrawRequest.authorizationCode());

        // 2. Apple 서버에 탈퇴 요청
        appleOAuthService.revoke(accessToken);

        // 3. 자체 회원 탈퇴 진행
        memberService.withdrawMember(authenticationMember.id());
    }
}