package com.dnd.dndtravel.auth.service;

import com.dnd.dndtravel.auth.domain.AuthToken;
import com.dnd.dndtravel.auth.repository.AuthTokenRepository;
import com.dnd.dndtravel.member.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private static final String secretKey = "${JWT_SECRET_KEY}";

    @Transactional
    public AuthToken issue(final Member member) {
        final Optional<AuthToken> byMemberId = authTokenRepository.findByMemberId(member.getId());
        return getInnerAuthToken(member, byMemberId);
    }

    private AuthToken getInnerAuthToken(Member member, Optional<AuthToken> byMemberId) {
        AuthToken authToken;

        if (byMemberId.isEmpty()) {
            String accessToken = createAccessToken(member.getId());  // Access Token 생성 시 MemberId 포함
            authToken = authTokenRepository.save(AuthToken.of(member.getId()));
        } else {
            authToken = byMemberId.get();
            authToken.reIssuance();
        }
        return authToken;
    }

    private String createAccessToken(Long memberId) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + 1000 * 60 * 60 * 24)) //만료 기간 1일
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}