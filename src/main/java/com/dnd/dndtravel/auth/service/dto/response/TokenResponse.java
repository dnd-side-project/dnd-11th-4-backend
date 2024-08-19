package com.dnd.dndtravel.auth.service.dto.response;

import lombok.Getter;

@Getter
public record TokenResponse(String accessToken) {
}