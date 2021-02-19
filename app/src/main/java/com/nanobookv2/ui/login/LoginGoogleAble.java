package com.nanobookv2.ui.login;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public interface LoginGoogleAble {

    int RC_SIGN_IN = 6868;
    int SIGN_UP = 0;
    int LOGIN = 1;
    default GoogleSignInClient getSignInClient(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("233577359971-5e1kbulg2a57fchd8e6m93fj8lron04d.apps.googleusercontent.com")
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(context, gso);
    }

}


