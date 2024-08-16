package com.dnd.dndtravel.apple;

import com.dnd.dndtravel.apple.dto.ApplePublicKeys;
import com.dnd.dndtravel.apple.dto.AppleUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AppleOauthService {
    private final AppleTokenParser appleTokenParser;
    private final AppleClient appleClient;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

    private final String DEFAULT_NAME = "apple";
    private final String CLAIM_EMAIL = "email";

    public AppleUser createAppleUser(final String appleToken) {
        final Map<String, String> appleTokenHeader = appleTokenParser.parseHeader(appleToken);
        final ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        final PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenHeader, applePublicKeys);
        final Claims claims = appleTokenParser.extractClaims(appleToken, publicKey);
        return new AppleUser(DEFAULT_NAME, claims.get(CLAIM_EMAIL, String.class));
    }
}
