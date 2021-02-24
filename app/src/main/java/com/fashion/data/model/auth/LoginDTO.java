package com.fashion.data.model.auth;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class LoginDTO {
    public static class EmailRequest {
        @SerializedName("email")
        public String email;
        @SerializedName("password")
        public String password;
    }
    public static class FacebookRequest {
        @SerializedName("facebookToken")
        public String facebookToken;
        @SerializedName("facebookId")
        public String facebookId;
    }

    public static class GoogleRequest {
        @SerializedName("googleIdToken")
        public String googleIdToken;
        @SerializedName("platform")
        public String platform = "web";
    }

    public static class AppleRequest {
        @SerializedName("appleToken")
        public String appleToken;
        @SerializedName("appleId")
        public String appleId;
    }

    public static class VerifyForgotPasswordRequest {
        @SerializedName("code")
        public String code;
        @SerializedName("newPassword")
        public String newPassword;
    }

    public static class Response {
        @SerializedName("token")
        public String token;
        @SerializedName("expireIn")
        public int expireIn;
        @SerializedName("createdAt")
        public Timestamp createdAt;
        @SerializedName("updatedAt")
        public Timestamp updatedAt;
        @SerializedName("app")
        public String app;
    }
}
