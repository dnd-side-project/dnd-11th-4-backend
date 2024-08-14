package com.dnd.dndtravel.auth.repository;

import com.dnd.dndtravel.auth.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);

    List<AuthToken> findAllByMemberId(Long memberId);

    Optional<AuthToken> findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}