package com.dnd.dndtravel.auth.controller;

import com.dnd.dndtravel.auth.apple.AppleOauthService;
import com.dnd.dndtravel.auth.apple.dto.AppleUser;
import com.dnd.dndtravel.auth.domain.AuthToken;
import com.dnd.dndtravel.auth.service.dto.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.auth.service.AuthTokenService;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthTokenController {

    private final AuthTokenService authTokenService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOauthLogin(@RequestBody final AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.getAppleToken());
        Member member = memberService.saveMember(appleUser);
        AuthToken authToken = authTokenService.issue(member);
        return ResponseEntity.ok(new TokenResponse(authToken));
    }
}
