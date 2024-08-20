package com.dnd.dndtravel.auth.controller;

import com.dnd.dndtravel.auth.apple.AppleOauthService;
import com.dnd.dndtravel.auth.apple.dto.AppleUser;
import com.dnd.dndtravel.auth.config.JwtProvider;
import com.dnd.dndtravel.auth.repository.AuthTokenRepository;
import com.dnd.dndtravel.auth.service.dto.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class AuthTokenController {

    private final MemberService memberService;
    private final AppleOauthService appleOauthService;
    private final JwtProvider jwtProvider;
    private final AuthTokenRepository authTokenRepository;

    @PostMapping("/login/oauth2/apple")
    public TokenResponse appleOauthLogin(@RequestBody AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.appleToken());
        Member member = memberService.saveMember(appleUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), null, Collections.emptyList());
        String accessToken = jwtProvider.createToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(member.getId());
        authTokenRepository.saveRefreshToken(member.getId(), refreshToken); // refreshToken은 DB에 저장

        return new TokenResponse(accessToken);
    }
}