package com.nanobookv2.ui.view_model;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.nanobookv2.R;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.core.config.SharePrefConfig;
import com.nanobookv2.core.ui.ResponseError;
import com.nanobookv2.data.DataManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import retrofit2.HttpException;
import retrofit2.Response;

public class NanoViewModel extends ViewModel {
    public int currentAction = 0;
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isShowPopup = new MutableLiveData<>();
    public final MutableLiveData<String> message = new MutableLiveData<>();
    public final MutableLiveData<Integer> idMessage = new MutableLiveData<>();
    public final MutableLiveData<List<String>> errors = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLogoutRequired = new MutableLiveData<>();
    public final MutableLiveData<Boolean> networkInvalid = new MutableLiveData<>();
    @Inject
    protected CompositeDisposable compositeDisposable;
    @Inject
    public DataManager dataManager;
    @Inject
    public SessionManager sessionManager;
    public Gson gson = new Gson();

    @Inject
    public NanoViewModel() {
        message.postValue(null);
        errors.postValue(new ArrayList<>());
    }

    protected Consumer<Throwable> handleError() {
        return throwable -> {
            isLoading.postValue(false);

            if (throwable instanceof HttpException) {
                HttpException exception = (HttpException) throwable;
                Response<?> restResponse = exception.response();
                assert restResponse != null;
                assert restResponse.errorBody() != null;
                if (restResponse.code() == 401 || restResponse.code() == 403) {
                    isLogoutRequired.postValue(true);
                    logoutLocal();
                }
                String jsonError = restResponse.errorBody().string();
                if (!jsonError.startsWith("<!DOCTYPE html>")) {
                    ResponseError error = gson.fromJson(jsonError, ResponseError.class);
                    message.postValue(error.message);
                } else message.postValue(jsonError);

            } else if (throwable instanceof SocketTimeoutException
                    || throwable instanceof ConnectException
                    || throwable instanceof UnknownHostException) {
                idMessage.postValue(R.string.message_timeout);
            } else
                throwable.printStackTrace();
        };
    }

    protected Action onCompleted() {
        return () -> {
            System.out.println("done");
            isLoading.postValue(false);
        };
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    protected <T> Observable<T> preConsumer(Observable<T> observable) {
        return observable.compose(upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()));
    }

    protected <T> Single<T> preConsumer(Single<T> observable) {
        return observable.compose(upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()));
    }

    protected Completable preConsumer(Completable completable){
        return completable.compose(upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()));
    }

    public void logoutLocal() {
        isLogoutRequired.postValue(true);
        sessionManager.accessToken = null;
        sessionManager.userId = null;
        dataManager.deleteSavedData(SharePrefConfig.KEY_TOKEN);
    }
}
