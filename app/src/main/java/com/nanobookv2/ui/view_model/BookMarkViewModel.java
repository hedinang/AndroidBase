package com.nanobookv2.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.nanobookv2.core.ResponseBase;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.data.Chapter;
import com.nanobookv2.data.model.BookMarkDTO;
import com.nanobookv2.data.remote.ApiBookService;
import com.nanobookv2.ui.broadcast.NetworkConnectReceiver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.nanobookv2.core.config.NetworkConfig.PAGE_FIRST;

public class BookMarkViewModel extends NanoViewModel {
    public final MutableLiveData<Boolean> addSuccess = new MutableLiveData<>();
    @Inject
    ApiBookService apiBookService;

    @Inject
    public BookMarkViewModel() {
        super();
    }

    public void addBookMark(final String bookId) {
        if (NetworkConnectReceiver.isConnected()) {
            BookMarkDTO.Request request = new BookMarkDTO.Request();
            request.bookId = bookId;
            Disposable disposable = preConsumer(apiBookService
                    .addBookMark(sessionManager.userId, request)
                    .doOnError(handleError()))
                    .subscribe(response -> {
                                if (response.data!=null) addSuccess.postValue(true);
                            },
                            handleError()
                    );

            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void removeBookMark(final String bookId) {
        if (NetworkConnectReceiver.isConnected()) {
            Disposable disposable = preConsumer(apiBookService
                    .removeBookMark(sessionManager.userId, bookId)
                    .doOnError(handleError()))
                    .subscribe(response -> {
                                if (response.data!=null) addSuccess.postValue(true);
                            },
                            handleError()
                    );

            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }
}
