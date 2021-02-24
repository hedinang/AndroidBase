package com.fashion.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.fashion.core.SessionManager;
import com.fashion.data.DataManager;
import com.fashion.data.remote.ApiAuthService;

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
