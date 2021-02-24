package com.fashion.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.fashion.data.model.BookMarkDTO;
import com.fashion.data.remote.ApiBookService;
import com.fashion.ui.broadcast.NetworkConnectReceiver;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
