package com.dnd.dndtravel.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    private AuthToken(Long memberId, String accessToken, String refreshToken){
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthToken of(Long memberId) {
        return AuthToken.builder()
                .memberId(memberId)
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .build();
    }

    public void reIssuance() {
        this.accessToken = UUID.randomUUID().toString();
        this.refreshToken = UUID.randomUUID().toString();
    }
}
