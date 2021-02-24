package com.fashion.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.fashion.data.remote.ApiAuthService;
import com.fashion.ui.broadcast.NetworkConnectReceiver;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import retrofit2.Response;

public class SplashViewModel extends NanoViewModel {
    public static final int ACTION_LOAD_ME = 0;
    public final MutableLiveData<Integer> isAuthenticated = new MutableLiveData<>();
    public static final int PENDING = -1;
    public static final int REJECTED = 0;
    public static final int APPROVED = 1;
    @Inject
    ApiAuthService authService;

    @Inject
    SplashViewModel() {
        super();
        isAuthenticated.postValue(PENDING);
    }

    public void getMe() {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(authService.getMe().doOnError(handleError()))
                    .subscribe(
                            response -> {
                                isAuthenticated.postValue(APPROVED);
                            },
                            throwable -> {
                                if (throwable instanceof HttpException) {
                                    HttpException exception = (HttpException) throwable;
                                    Response<?> restResponse = exception.response();
                                    assert restResponse != null;
                                    assert restResponse.errorBody() != null;
                                    if (restResponse.code() == 401 || restResponse.code() == 403) {
                                        logoutLocal();
                                        isAuthenticated.postValue(REJECTED);
                                    }
                                }
                                throwable.printStackTrace();
                            },
                            onCompleted()
                    );
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }
}
