package com.dnd.dndtravel.auth.repository;

import com.dnd.dndtravel.auth.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByMemberId(Long memberId);

}