package com.dnd.dndtravel.auth.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public record AppleLoginRequest(String appleToken) {

    public AppleLoginRequest(@JsonProperty("appleToken") final String appleToken) {
        this.appleToken = appleToken;
    }
}
