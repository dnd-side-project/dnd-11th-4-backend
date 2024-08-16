package com.dnd.dndtravel.auth.service;

import com.dnd.dndtravel.auth.domain.AuthToken;
import com.dnd.dndtravel.auth.dto.AuthMember;
import com.dnd.dndtravel.auth.repository.AuthTokenRepository;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;

    private final MemberService memberService;

    public static ConcurrentHashMap<String, AuthMember> accessTokenMap = new ConcurrentHashMap(256);

    @Transactional
    public AuthToken issue(final Member member) {
        final Optional<AuthToken> byMemberId = authTokenRepository.findByMemberId(member.getId());
        AuthToken authToken;
        if (byMemberId.isEmpty()) {
            authToken = authTokenRepository.save(AuthToken.of(member.getId()));
        } else {
            authToken = byMemberId.get();
            final String accessToken = authToken.getAccessToken();
            accessTokenMap.remove(accessToken);
            authToken.reIssuance();
        }
        log.info("memberId: " + member.getId());
        log.info("access 토근 발급: " + authToken.getAccessToken());
        log.info("refresh 토근 발급: " + authToken.getRefreshToken());
        final AuthMember authMember = new AuthMember(member);
        accessTokenMap.put(authToken.getAccessToken(), authMember);
        return authToken;
    }
}
