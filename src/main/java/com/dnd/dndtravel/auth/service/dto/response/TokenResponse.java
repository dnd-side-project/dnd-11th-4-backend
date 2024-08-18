package com.dnd.dndtravel.auth.service.dto.response;

import com.dnd.dndtravel.auth.domain.AuthToken;
import lombok.Getter;

@Getter
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(final AuthToken authToken) {
        this.accessToken = authToken.getAccessToken();
        this.refreshToken = authToken.getRefreshToken();
    }
}
