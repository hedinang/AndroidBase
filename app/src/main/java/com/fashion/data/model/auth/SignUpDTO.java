package com.fashion.data.model.auth;

import com.google.gson.annotations.SerializedName;

public class SignUpDTO {
    public static class EmailRequest {
        @SerializedName("email")
        public String email;
        @SerializedName("password")
        public String password;
        public String passwordConfirm;
    }

    public static class Response {
        @SerializedName("id")
        public String id;
        @SerializedName("email")
        public String email;
        @SerializedName("token")
        public String token;
    }

    public static class FacebookRequest {

        public String fullName;
        public String displayName;
        public String mobile = "";
        public String email;
        public String dateOfBirth = "";
        public String city = "";
        public String district = "";
        public String ward = "";
        public String address = "";
        public String facebookToken;
        public String facebookId;
    }

    public static class GoogleRequest {
        public String fullName;
        public String displayName;
        public String mobile = "";
        public String email;
        public String dateOfBirth = "";
        public String city = "";
        public String district = "";
        public String ward = "";
        public String address = "";
        public String googleIdToken;
        public String platform = "web";

    }

    public static class AppleRequest {
        public String name;
        public String appleId;
        public String appleToken;
    }
}
