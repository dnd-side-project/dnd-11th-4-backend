package com.dnd.dndtravel.auth.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "memberId";
    private final long tokenValidityInMilliseconds;
    private final String secretKey;
    private Key key;

    public JwtProvider(
            @Value("${JWT_SECRET_KEY}") String secretKey,
            @Value("86400") long tokenValidityInSeconds) {
        this.secretKey = secretKey;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    // secretKey 값을 Base64 Decode 해서 key 변수에 할당
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT Access 토큰 생성
    public String createToken(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds); //1일

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim(AUTHORITIES_KEY, memberId)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    // Refresh 토큰 생성
    public String createRefreshToken(Long memberId) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds * 14); // 14일

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    // authentication 객체 생성
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.get(AUTHORITIES_KEY), token, authorities);
    }

    // Token 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT Signature");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원 되지 않는 JWT 토큰");
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 JWT 토큰");
        }

        return false;
    }
}
