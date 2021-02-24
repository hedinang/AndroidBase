package com.fashion.ui.view_model;

import android.content.Intent;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.fashion.R;
import com.fashion.core.config.SharePrefConfig;
import com.fashion.data.model.auth.LoginDTO;
import com.fashion.data.model.auth.SignUpDTO;
import com.fashion.data.remote.ApiAuthService;
import com.fashion.ui.broadcast.NetworkConnectReceiver;
import com.fashion.ui.login.LoginGoogleAble;

import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class AuthViewModel extends NanoViewModel {
    public final MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isConfirmEmailSuccess = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isVerifyForgotPasswordSuccess = new MutableLiveData<>();

    @Inject
    ApiAuthService authService;

    @Inject
    public AuthViewModel() {
        super();
    }

    public void signUp(SignUpDTO.EmailRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            if (validateSignUp(request.email, request.password, request.passwordConfirm)) {
                isLoading.postValue(true);
                Disposable disposable = preConsumer(authService.signUp(request))
                        .subscribe(
                                response -> {
                                    sessionManager.accessToken = response.data.token;
                                    dataManager.put(SharePrefConfig.KEY_TOKEN, response.data.token);
                                    isAuthenticated.postValue(true);
                                },
                                handleError(),
                                onCompleted()
                        );
                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void signUpFacebook(SignUpDTO.FacebookRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(authService.signUpByFacebook(request))
                    .subscribe(
                            response -> {
                                sessionManager.accessToken = response.data.token;
                                dataManager.put(SharePrefConfig.KEY_TOKEN, response.data.token);
                                isAuthenticated.postValue(true);
                            },
                            handleError(),
                            onCompleted()
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    private void signUpGoogle(SignUpDTO.GoogleRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            isLoading.postValue(true);
            Disposable disposable = preConsumer(authService.signUpByGoogle(request))
                    .subscribe(
                            response -> {
                                sessionManager.accessToken = response.data.token;
                                dataManager.put(SharePrefConfig.KEY_TOKEN, response.data.token);
                                isAuthenticated.postValue(true);
                            },
                            handleError(),
                            onCompleted()
                    );
            compositeDisposable.add(disposable);

        } else {
            networkInvalid.postValue(true);
        }
    }

    public void signUpApple(SignUpDTO.AppleRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(authService.signUpByApple(request))
                    .subscribe(
                            response -> {

                            },
                            handleError(),
                            onCompleted()
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void login(LoginDTO.EmailRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            if (validatorLogin(request.email, request.password)) {
                isLoading.postValue(true);
                Disposable disposable = preConsumer(authService.login(request))
                        .subscribe(
                                response -> {
                                    isAuthenticated.postValue(true);
                                    sessionManager.accessToken = response.data.token;
                                    dataManager.put(SharePrefConfig.KEY_TOKEN, response.data.token);
                                },
                                handleError(),
                                onCompleted()
                        );
                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loginFacebook(LoginDTO.FacebookRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(authService.loginByFacebook(request))
                    .subscribe(
                            response -> {

                            },
                            handleError(),
                            onCompleted()
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loginApple(LoginDTO.AppleRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(authService.loginByApple(request))
                    .subscribe(
                            response -> {

                            },
                            handleError(),
                            onCompleted()
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loginGoogle(LoginDTO.GoogleRequest request) {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(authService.loginByGoogle(request))
                    .subscribe(
                            response -> {
                                sessionManager.accessToken = response.data.token;
                                dataManager.put(SharePrefConfig.KEY_TOKEN, response.data.token);
                                isAuthenticated.postValue(true);
                            },
                            handleError(),
                            onCompleted()
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void confirmEmail(String email){
        if (NetworkConnectReceiver.isConnected()) {
            isLoading.postValue(true);
            Disposable disposable = preConsumer(authService.forgotPassword(email))
                    .subscribe(
                            () ->{
                                isLoading.postValue(false);
                                isConfirmEmailSuccess.postValue(true);
                            },
                            throwable -> {
                                isLoading.postValue(false);
                                if (throwable instanceof SocketTimeoutException) {
                                    idMessage.postValue(R.string.message_timeout);
                                    isConfirmEmailSuccess.postValue(false);
                                } else {
                                    isConfirmEmailSuccess.postValue(true);
                                }
                                throwable.printStackTrace();
                            }
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void verifyForgotPassword(String code, String password, String passwordConfirm){
        if (NetworkConnectReceiver.isConnected()) {
            if (validateChangePassword(password, passwordConfirm)){
                isLoading.postValue(true);
                LoginDTO.VerifyForgotPasswordRequest request = new LoginDTO.VerifyForgotPasswordRequest();
                request.code = code;
                request.newPassword = password;
                Disposable disposable = preConsumer(authService.verifyForgotPassword(request))
                        .subscribe(
                                response -> {
                                    isVerifyForgotPasswordSuccess.postValue(true);
                                },
                                handleError(),
                                onCompleted()
                        );
                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void handleResultAuthGoogle(int type, Intent data) {
        try {
            Task<GoogleSignInAccount> completedTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                if (type == LoginGoogleAble.SIGN_UP) {
                    SignUpDTO.GoogleRequest request = new SignUpDTO.GoogleRequest();
                    request.fullName = acct.getFamilyName() + " " + acct.getGivenName();
                    request.displayName = acct.getDisplayName();
                    request.email = acct.getEmail();
                    request.googleIdToken = acct.getIdToken();
                    signUpGoogle(request);
                } else {
                    LoginDTO.GoogleRequest request = new LoginDTO.GoogleRequest();
                    request.googleIdToken = acct.getIdToken();
                    loginGoogle(request);
                }

            } else {
                idMessage.postValue(R.string.message_sign_up_google_error);
            }
        } catch (ApiException e) {
            System.out.println(e.getMessage());
            idMessage.postValue(R.string.message_sign_up_google_error);
        }
    }


    private boolean validatorLogin(final String email, final String password) {

        if (TextUtils.isEmpty(email)) {
            idMessage.postValue(R.string.message_email_empty);
            return false;
        }

        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            idMessage.postValue(R.string.message_email_invalid);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            idMessage.postValue(R.string.message_password_empty);
            return false;
        }
        if (password.length() > 20 || password.length() < 4) {
            idMessage.postValue(R.string.message_password_length_error);
            return false;
        }
        return true;
    }

    private boolean validateSignUp(final String email, final String password, final String passwordConfirm) {
        if (!validatorLogin(email, password)) {
            return false;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            idMessage.postValue(R.string.message_confirm_password_empty);
            return false;
        }
        if (passwordConfirm.length() > 20 || passwordConfirm.length() < 4) {
            idMessage.postValue(R.string.message_password_length_error);
            return false;
        }

        if (!password.equals(passwordConfirm)) {
            idMessage.postValue(R.string.message_confirm_password_error);
            return false;
        }
        return true;
    }

    public boolean isValidEmail(final String email){
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidCode(final String code){
        if (TextUtils.isEmpty(code)) {
            idMessage.postValue(R.string.message_code_empty);
            return false;
        }

        if (code.length() != 6) {
            idMessage.postValue(R.string.message_code_error);
            return false;
        }

        return true;
    }

    private boolean validateChangePassword(final String password, final String passwordConfirm){
        if (TextUtils.isEmpty(password)) {
            idMessage.postValue(R.string.message_password_empty);
            return false;
        }

        if (TextUtils.isEmpty(passwordConfirm)) {
            idMessage.postValue(R.string.message_confirm_password_empty);
            return false;
        }
        if (password.length() > 20 || password.length() < 4 || passwordConfirm.length() > 20 || passwordConfirm.length() < 4) {
            idMessage.postValue(R.string.message_password_length_error);
            return false;
        }

        if (!password.equals(passwordConfirm)) {
            idMessage.postValue(R.string.message_confirm_password_error);
            return false;
        }

        return true;
    }
}
