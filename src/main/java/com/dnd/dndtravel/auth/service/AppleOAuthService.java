package com.dnd.dndtravel.auth.service;

import com.dnd.dndtravel.auth.exception.RequireReAuthenticationException;
import com.dnd.dndtravel.auth.service.dto.response.AppleSocialTokenInfoResponse;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.springframework.stereotype.Component;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import com.dnd.dndtravel.auth.service.dto.response.AppleIdTokenPayload;
import com.dnd.dndtravel.auth.config.AppleProperties;
import com.dnd.dndtravel.auth.service.dto.response.AppleTokenResponse;

/**
 * private key와 기타 설정값들로 client secret을 생성한다
 * client_secret: 개발자가 생성한 Secret JWT 토큰, 개발자 계정과 연결된 Apple로 로그인 개인 키를 사용,  authorization code와  refresh token 검증 요청에 해당 파라미터 필요
 * client_secret을 만드는 관련 docs
 * - https://developer.apple.com/documentation/accountorganizationaldatasharing/creating-a-client-secret
 *
 * clinet_secret jwt 토큰 디코딩 예시 형식
 * {
 * //JWT 헤더
 *     "alg": "ES256", // 토큰에 서명된 알고리즘
 *     "kid": "ABC123DEFG" // 개발자 계정과 연결된 계정 + private key 로 만들어진 식별자
 * }
 * {
 *     "iss": "DEF123GHIJ",// 팀ID
 *     "iat": 1437179036, //
 *     "exp": 1493298100, // 만료시간
 *     "aud": "https://appleid.apple.com",
 *     "sub": "com.mytest.app"
 * }
 */

@RequiredArgsConstructor
@Component
public class AppleOAuthService {
    private final AppleClient appleClient;
    private final AppleProperties appleProperties;

    public AppleTokenResponse get(String authorizationCode) {
        AppleSocialTokenInfoResponse appleSocialTokenInfoResponse = appleClient.getIdToken(
            appleProperties.getClientId(),
            generateClientSecret(),
            appleProperties.getGrantType(),
            authorizationCode
        );

        AppleIdTokenPayload appleIdTokenPayload = TokenDecoder.decodePayload(appleSocialTokenInfoResponse.idToken(), AppleIdTokenPayload.class);
        return AppleTokenResponse.of(appleIdTokenPayload, appleSocialTokenInfoResponse.refreshToken());
    }

    public void revoke(String refreshToken) {
        try {
            appleClient.revoke(
                appleProperties.getClientId(),
                generateClientSecret(),
                refreshToken,
                "refresh_token"
            );
        } catch (Exception e) {
            // invalid_grant 응답메시지로, 정확한 예외Response 확인후 Refresh Token 만료되었다는 예외라면 재인증 요청하게끔 하는 코드로 수정하는것을 권장
            throw new RequireReAuthenticationException(e);
        }
    }

    private String generateClientSecret() {
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        return Jwts.builder()
            .header().add(JwsHeader.KEY_ID, appleProperties.getKeyId()).and()
            .issuer(appleProperties.getTeamId())
            .audience().add(appleProperties.getAudience()).and()
            .subject(appleProperties.getClientId())
            .expiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
            .issuedAt(new Date())
            .signWith(getPrivateKey())
            .compact();
    }

    private PrivateKey getPrivateKey() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(appleProperties.getPrivateKey());

            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);
            return converter.getPrivateKey(privateKeyInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }
}
