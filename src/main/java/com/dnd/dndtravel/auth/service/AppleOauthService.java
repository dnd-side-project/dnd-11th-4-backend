package com.dnd.dndtravel.auth.service;

import com.dnd.dndtravel.auth.apple.AppleClient;
import com.dnd.dndtravel.auth.apple.ApplePublicKeyGenerator;
import com.dnd.dndtravel.auth.apple.AppleTokenParser;
import com.dnd.dndtravel.auth.service.dto.response.AppleUserResponse;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@RequiredArgsConstructor
@Component
public class AppleOauthService {
    private static final String DEFAULT_NAME = "apple";
    private static final String CLAIM_EMAIL = "email";
    private final AppleTokenParser appleTokenParser;
    private final AppleClient appleClient;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

    public AppleUserResponse createAppleUser(String appleToken) {
        PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenParser.parseHeader(appleToken), appleClient.getApplePublicKeys());
        Claims claims = appleTokenParser.extractClaims(appleToken, publicKey); // apple에게 받은 토큰을 까서, claim을 얻는다.
        return new AppleUserResponse(DEFAULT_NAME, claims.get(CLAIM_EMAIL, String.class));
    }
}
