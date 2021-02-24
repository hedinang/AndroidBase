package com.fashion.data;

import com.fashion.data.db.DataBaseHelper;
import com.fashion.data.local.SharePrefsHelper;

import javax.inject.Inject;

//@Singleton
public class DataManagerImp implements DataManager {

    DataBaseHelper dataBaseHelper;
    SharePrefsHelper sharePrefsHelper;

    @Inject
    public DataManagerImp(DataBaseHelper dataBaseHelper,
                          SharePrefsHelper sharePrefsHelper) {
        this.dataBaseHelper = dataBaseHelper;
        this.sharePrefsHelper = sharePrefsHelper;
    }

    /*database helper*/

    /*share prefs helper*/
    @Override
    public void put(String key, String value) {
        this.sharePrefsHelper.put(key, value);
    }

    @Override
    public void put(String key, int value) {
        this.sharePrefsHelper.put(key, value);
    }

    @Override
    public void put(String key, float value) {
        this.sharePrefsHelper.put(key, value);
    }

    @Override
    public void put(String key, boolean value) {
        this.sharePrefsHelper.put(key, value);
    }

    @Override
    public String get(String key, String defaultValue) {
        return this.sharePrefsHelper.get(key, defaultValue);
    }

    @Override
    public Integer get(String key, int defaultValue) {
        return this.sharePrefsHelper.get(key, defaultValue);
    }

    @Override
    public Float get(String key, float defaultValue) {
        return this.sharePrefsHelper.get(key, defaultValue);
    }

    @Override
    public Boolean get(String key, boolean defaultValue) {
        return this.sharePrefsHelper.get(key, defaultValue);
    }

    @Override
    public void deleteSavedData(String key) {
        this.sharePrefsHelper.deleteSavedData(key);
    }
}
