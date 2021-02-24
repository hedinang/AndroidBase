package com.fashion.ui.login;

import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fashion.NanoApplication;

public interface LoginFacebookAble {
    CallbackManager callbackManager = CallbackManager.Factory.create();
    default void initFacebookSDK(){
        FacebookSdk.sdkInitialize(NanoApplication.getInstance());
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");

                    }

                    @Override
                    public void onCancel() {
                        System.out.println("cancel login facebook");
                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });
    }


}
