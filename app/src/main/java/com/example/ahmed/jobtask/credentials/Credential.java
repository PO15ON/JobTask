package com.example.ahmed.jobtask.credentials;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed on 2/11/2018.
 */

public class Credential {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("id")
    private String id;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("scope")
    private boolean scope;

    @SerializedName("refresh_token")
    private String refreshToken;

    public String getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isScope() {
        return scope;
    }

    public void setScope(boolean scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
