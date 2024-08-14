package com.dnd.dndtravel.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AppleRevokeRequest {

    private final String appleToken;

    private final String authorizationCode;

    @JsonCreator
    public AppleRevokeRequest(@JsonProperty("appleToken")final String appleToken,
                              @JsonProperty("authorizationCode")final String authorizationCode) {
        this.appleToken = appleToken;
        this.authorizationCode = authorizationCode;
    }

}
