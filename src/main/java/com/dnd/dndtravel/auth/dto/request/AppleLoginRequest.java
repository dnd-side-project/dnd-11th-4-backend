package com.dnd.dndtravel.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AppleLoginRequest {

    private final String appleToken;

    @JsonCreator
    public AppleLoginRequest(@JsonProperty("appleToken")final String appleToken) {
        this.appleToken = appleToken;
    }
}
