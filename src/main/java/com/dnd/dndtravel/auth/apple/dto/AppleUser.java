package com.dnd.dndtravel.auth.apple.dto;

import lombok.Getter;

@Getter
public record AppleUser(String name, String email) {
}
