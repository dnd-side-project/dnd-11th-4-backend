package com.dnd.dndtravel.auth.dto;

import com.akkin.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthMember {

    private final Long id;

    private final String name;

    private final String email;

    private LocalDateTime createdAt;

    public AuthMember(final Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.createdAt = LocalDateTime.now();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
