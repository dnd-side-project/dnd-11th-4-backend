package com.dnd.dndtravel.auth.repository;

import com.dnd.dndtravel.auth.domain.RefreshTokenCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<RefreshTokenCollector, Long> {
    @Modifying
    @Query("UPDATE RefreshTokenCollector a SET a.refreshToken = :refreshToken WHERE a.id = :id")
    void saveRefreshToken(@Param("id") Long id, @Param("refreshToken") String refreshToken);
}