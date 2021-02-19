package com.nanobookv2.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.nanobookv2.core.SessionManager;
import com.nanobookv2.data.DataManager;
import com.nanobookv2.data.remote.ApiAuthService;

import javax.inject.Inject;

public class MainViewModel extends NanoViewModel{
    @Inject
    DataManager dataManager;
    @Inject
    SessionManager sessionManager;
    @Inject
    ApiAuthService authService;
    public MutableLiveData<String> hello = new MutableLiveData<>();

    @Inject
    public MainViewModel(){
        super();
        hello.postValue("Xin chaof 100 ae");
    }
}
