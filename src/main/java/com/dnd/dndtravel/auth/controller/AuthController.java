package com.dnd.dndtravel.auth.controller;

import com.dnd.dndtravel.auth.controller.request.AppleWithdrawRequest;
import com.dnd.dndtravel.auth.controller.request.ReIssueTokenRequest;
import com.dnd.dndtravel.auth.controller.swagger.AuthControllerSwagger;
import com.dnd.dndtravel.auth.service.AuthService;
import com.dnd.dndtravel.auth.service.JwtTokenService;
import com.dnd.dndtravel.auth.controller.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.auth.service.dto.response.ReissueTokenResponse;
import com.dnd.dndtravel.config.AuthenticationMember;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerSwagger {
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOAuthLogin(@RequestBody @Valid AppleLoginRequest request) {
        return authService.processAppleLogin(request.authorizationCode(), request.selectedColor())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/reissue/token")
    public ReissueTokenResponse reissueToken(@RequestBody @Valid ReIssueTokenRequest reissueTokenRequest) {
        return jwtTokenService.reIssue(reissueTokenRequest.refreshToken());
    }

    @DeleteMapping("/withdraw")
    public void withdraw(
        @RequestBody @Valid AppleWithdrawRequest withdrawRequest,
        AuthenticationMember authenticationMember
    ) {
        authService.processAppleRevoke(withdrawRequest.refreshToken(), authenticationMember.id());
    }
}