package com.dnd.dndtravel.auth.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AppleLoginRequest {

    private final String appleToken;

    public AppleLoginRequest(@JsonProperty("appleToken")final String appleToken) {
        this.appleToken = appleToken;
    }
}
