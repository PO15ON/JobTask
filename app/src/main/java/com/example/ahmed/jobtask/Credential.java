package com.example.ahmed.jobtask;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import static android.content.ContentValues.TAG;

/**
 * Created by Ahmed on 2/11/2018.
 */

public class Credential {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("scope")
    private boolean scope;

    @SerializedName("refresh_token")
    private String refreshToken;

    public Credential(String accessToken, int expiresIn, String tokenType, boolean scope, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.scope = scope;
        this.refreshToken = refreshToken;
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
        Log.d(TAG, "getRefreshToken: " + refreshToken);
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
