package com.dnd.dndtravel.auth.service.dto;

import com.dnd.dndtravel.member.domain.Member;
import lombok.Getter;

@Getter
public class AuthMember {

    private final Long id;

    private final String name;

    private final String email;

    public AuthMember(final Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
