package com.dnd.dndtravel.auth.repository;


import java.util.Optional;

import com.dnd.dndtravel.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByMemberId(Long memberId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}