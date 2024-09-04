package com.dnd.dndtravel.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import com.dnd.dndtravel.auth.exception.JwtTokenExpiredException;
import com.dnd.dndtravel.auth.exception.JwtTokenDecodingException;

@Component
public class JwtProvider {
    private static final String CLAIM_CONTENT = "memberId";
    private final long accessTokenExpiredTime;
    private final long refreshTokenExpiredTime;
    private final SecretKey secretKey;

    public JwtProvider(
        @Value("${jwt.secret-key}") String secretKeyString,
        @Value("${jwt.access-token-expired-ms}") long accessTokenExpiredTime,
        @Value("${jwt.refresh-token-expired-ms}") long refreshTokenExpiredTime
        ) {
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyString));
    }

    public String accessToken(Long memberId) {
        return Jwts.builder()
            .claim(CLAIM_CONTENT, memberId)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.accessTokenExpiredTime))
            .signWith(secretKey)
            .compact();
    }

    public String refreshToken() {
        return Jwts.builder()
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + this.refreshTokenExpiredTime))
            .signWith(secretKey)
            .compact();
    }

    public Claims parseClaims(String splitHeader) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(String.valueOf(this.secretKey))))
                    .build()
                    .parseSignedClaims(splitHeader);

        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException(e);
        } catch (JwtException e) {
            throw new JwtTokenDecodingException(e);
        }

        return claims.getPayload();
    }
}
