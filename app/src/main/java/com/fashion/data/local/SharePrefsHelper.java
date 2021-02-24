package com.fashion.data.local;

public interface SharePrefsHelper {

    String PREF_KEY_ACCESS_TOKEN = "access_token";

    void put(String key, String value);

    void put(String key, int value);

    void put(String key, float value);

    void put(String key, boolean value);

    String get(String key, String defaultValue);

    Integer get(String key, int defaultValue);

    Float get(String key, float defaultValue);

    Boolean get(String key, boolean defaultValue);

    void deleteSavedData(String key);
}
