package com.dnd.dndtravel.member.domain;

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

    public static Member of(String name, String email){
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }
}
