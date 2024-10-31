package com.dnd.dndtravel.auth.controller;

import java.util.Optional;

import com.dnd.dndtravel.auth.controller.request.AppleWithdrawRequest;
import com.dnd.dndtravel.auth.controller.request.ReIssueTokenRequest;
import com.dnd.dndtravel.auth.controller.swagger.AuthControllerSwagger;
import com.dnd.dndtravel.auth.service.AppleOAuthService;
import com.dnd.dndtravel.auth.service.JwtTokenService;
import com.dnd.dndtravel.auth.controller.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.service.dto.response.AppleTokenResponse;
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
public class AuthController implements AuthControllerSwagger {
    private final AppleOAuthService appleOAuthService;
    private final JwtTokenService jwtTokenService;
    private final MemberService memberService;

    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOAuthLogin(@RequestBody @Valid AppleLoginRequest appleLoginRequest) {
        // idToken 정보와 애플의 RefreshToken 얻기위해 Apple API 호출
        AppleTokenResponse appleTokenResponse = appleOAuthService.get(appleLoginRequest.authorizationCode());

        Member member = memberService.saveMember(
            appleTokenResponse.email(),
            appleTokenResponse.sub(),
            appleLoginRequest.selectedColor()
        );

        return generateTokenResponse(appleTokenResponse.appleRefreshToken(), member.getId());
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

    private ResponseEntity<TokenResponse> generateTokenResponse(String appleRefreshToken, Long memberId) {
        TokenResponse tokenResponse = jwtTokenService.generateTokens(memberId, appleRefreshToken);

        return Optional.ofNullable(tokenResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.noContent().build());
    }
}