package com.dnd.dndtravel.auth.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String refreshToken;

    private LocalDateTime expiredTime;

    private RefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiredTime = LocalDateTime.now();
    }

    public static RefreshToken of(Long memberId, String refreshToken) {
        return new RefreshToken(memberId, refreshToken);
    }

    public boolean isExpire() {
        return this.expiredTime.isBefore(LocalDateTime.now());
    }
}