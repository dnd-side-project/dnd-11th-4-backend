package com.dnd.dndtravel.member.domain;

import com.dnd.dndtravel.auth.apple.dto.AppleUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Builder
    private Member(String name, String email){
        this.name = name;
        this.email = email;
    }

    public static Member of(AppleUser appleUser) {
        return Member.builder()
                .name(appleUser.getName())
                .email(appleUser.getEmail())
                .build();
    }
}
