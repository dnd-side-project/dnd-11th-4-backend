package com.dnd.dndtravel.apple.dto;

public class AppleTokenResponse {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String tokenType;

    public AppleTokenResponse(String accessToken, String expiresIn, String refreshToken, String tokenType) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AppleTokenResponse() {
    }
}
