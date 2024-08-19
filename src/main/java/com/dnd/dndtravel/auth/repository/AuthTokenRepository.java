package com.dnd.dndtravel.auth.repository;

import com.dnd.dndtravel.auth.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    void saveRefreshToken(Long memberId, String refreshToken);
}