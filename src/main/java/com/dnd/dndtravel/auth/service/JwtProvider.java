package com.dnd.dndtravel.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    private static final String CLAIM_CONTENT = "memberId";
    private final long accessTokenExpiredTime;
    private final long refreshTokenExpiredTime;
    private final String secretKey;

    public JwtProvider(
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.access-token-expired-ms}") long accessTokenExpiredTime,
        @Value("${jwt.refresh-token-expired-ms}") long refreshTokenExpiredTime
       ) {
        this.secretKey = secretKey;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }

    public String accessToken(Long memberId) {
        return Jwts.builder()
            .claim(CLAIM_CONTENT, memberId)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.accessTokenExpiredTime))
            .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.secretKey)))
            .compact();
    }

    public String refreshToken(Long memberId) {
        return Jwts.builder()
            .claim(CLAIM_CONTENT, memberId)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.refreshTokenExpiredTime))
            .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.secretKey)))
            .compact();
    }

    public Claims parseClaims(String splitHeader) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.secretKey)))
                    .build()
                    .parseSignedClaims(splitHeader);

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("message", e); // 유효하지 않은 토큰
        } catch (JwtException e) {
            throw new RuntimeException("message", e); // 토큰 해독 실패
        }

        return claims.getPayload();
    }
}
