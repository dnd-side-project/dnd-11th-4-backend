package com.dnd.dndtravel.auth.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
서버에서 Apple 서버로 보내는 요청
 */
public record AppleRevokeRequest(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        @JsonProperty("token")
        String Token,
        @JsonProperty("token_type_hint")
        String tokenType
) {
}
