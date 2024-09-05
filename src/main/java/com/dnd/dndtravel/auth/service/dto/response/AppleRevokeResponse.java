package com.dnd.dndtravel.auth.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppleRevokeResponse(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        @JsonProperty("token")
        String token,
        @JsonProperty("token_type_hint")
        String tokenType
) {
}
