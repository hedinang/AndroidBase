package com.fashion.core;

import javax.inject.Singleton;

@Singleton
public class SessionManager {
    public String userId;
    public String accessToken;
    public String deviceId;
    public String language;

    public SessionManager(){

    }
}
