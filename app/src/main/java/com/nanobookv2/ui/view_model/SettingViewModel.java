package com.nanobookv2.ui.view_model;

import com.nanobookv2.core.config.SharePrefConfig;

import javax.inject.Inject;

public class SettingViewModel extends NanoViewModel {
    public Float textSizeReadScreen;
    public String backgroundReadScreen;
    public Float brightnessReadScreen;

    @Inject
    public SettingViewModel() {
        super();
    }

    public void loadSetting(){
        textSizeReadScreen = dataManager.get(SharePrefConfig.KEY_TEXT_SIZE, 16f);
        backgroundReadScreen = dataManager.get(SharePrefConfig.KEY_BACKGROUND_COLOR, "#ffffff");
        brightnessReadScreen = dataManager.get(SharePrefConfig.KEY_BRIGHTNESS, 0f);
    }

    public void saveSetting(){
        dataManager.put(SharePrefConfig.KEY_TEXT_SIZE, textSizeReadScreen);
        dataManager.put(SharePrefConfig.KEY_BACKGROUND_COLOR, backgroundReadScreen);
        dataManager.put(SharePrefConfig.KEY_BRIGHTNESS, brightnessReadScreen);
    }
}
