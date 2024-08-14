package com.dnd.dndtravel.auth.controller;

import com.dnd.dndtravel.apple.AppleOauthService;
import com.dnd.dndtravel.apple.dto.AppleUser;
import com.dnd.dndtravel.auth.domain.AuthToken;
import com.dnd.dndtravel.auth.dto.request.AppleLoginRequest;
import com.dnd.dndtravel.auth.dto.response.TokenResponse;
import com.dnd.dndtravel.auth.service.AuthTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthTokenController {

    private final AuthTokenService authTokenService;

    private final MemberService memberService;

    private final AppleOauthService appleOauthService;

    @Override
    @PostMapping("/login/oauth2/apple")
    public ResponseEntity<TokenResponse> appleOauthLogin(@RequestBody final AppleLoginRequest appleLoginRequest) {
        AppleUser appleUser = appleOauthService.createAppleUser(appleLoginRequest.getAppleToken());
        Member member = memberService.saveOrUpdateMember(appleUser);
        AuthToken authToken = authTokenService.issue(member);
        return ResponseEntity.ok(new TokenResponse(authToken));
    }
}
