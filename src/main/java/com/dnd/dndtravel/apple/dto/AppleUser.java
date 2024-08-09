package com.dnd.dndtravel.apple.dto;

import lombok.Getter;

@Getter
public class AppleUser {
    private final String name;
    private final String email;

    public AppleUser(final String name, final String email) {
        this.name = name;
        this.email = email;
    }
}
