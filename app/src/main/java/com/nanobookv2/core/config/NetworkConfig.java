package com.nanobookv2.core.config;

import com.nanobookv2.BuildConfig;

public class NetworkConfig {
    private static final String PREFIX = "https://";
    private static final String DOMAIN = "api.intekers.com";
    private static final String PORT = "";
    private static final String BASE_PATH = "/app/api/v1/";
    public static final String URL_BASE = BuildConfig.DEBUG
            ? PREFIX + DOMAIN + PORT + BASE_PATH
            : PREFIX + DOMAIN + PORT + BASE_PATH;

    public static final int CONNECTION_TIME_OUT = 10000;
    public static final String HEADER_DEVICE_ID = "device_uid";
    public static final String HEADER_LANGUAGE = "language";
    public static final String HEADER_AUTHENTICATION = "Authorization";
    public static final String[] ALLOW_PATHS= new String[]{
            BASE_PATH +"login(.*)",
            BASE_PATH +"signup(.*)",
            BASE_PATH +"forgotpasswords(.*)",
            BASE_PATH +"verifyforgotpasswords(.*)"};

    public static final int PAGE_FIRST = 0;
    public static final int PAGE_SIZE = 12;


}
