package com.nanobookv2.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.nanobookv2.data.model.Book;
import com.nanobookv2.data.remote.ApiBookService;
import com.nanobookv2.ui.broadcast.NetworkConnectReceiver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.nanobookv2.core.config.NetworkConfig.PAGE_FIRST;
import static com.nanobookv2.core.config.NetworkConfig.PAGE_SIZE;

public class ListBookViewModel extends NanoViewModel {
    public static final int ACTION_LOAD_FIRST = 0;
    public static final int ACTION_LOAD_MORE = 1;
    public static final int ACTION_ADD_MARK_BOOK = 2;
    public final MutableLiveData<List<Book>> books = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoadMore = new MutableLiveData<>();
    public int currentPage = 0;
    public boolean isEndPage = false;
    @Inject
    ApiBookService apiBookService;

    @Inject
    public ListBookViewModel() {
        super();
    }

    public void loadNewestBook() {
        if (NetworkConnectReceiver.isConnected()) {
            if (!isEndPage) {
                int page;
                if (currentPage == PAGE_FIRST)
                    isLoading.postValue(true);
                else {
                    isLoadMore.postValue(true);
                }
                page = currentPage + 1;
                Disposable disposable = preConsumer(apiBookService.getNewestBook(page, PAGE_SIZE).doOnError(handleError()))
                        .subscribe(response ->
                                {
                                    if (currentPage == PAGE_FIRST)
                                        isLoading.postValue(false);
                                    else {
                                        isLoadMore.postValue(false);
                                    }
                                    if (response.data.result.size() < PAGE_SIZE)
                                        isEndPage = true;

                                    currentPage++;
                                    books.postValue(response.data.result);
                                },
                                handleError()
                        );

                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loadTrendBook() {
        if (NetworkConnectReceiver.isConnected()) {
            if (!isEndPage) {
                int page;
                if (currentPage == PAGE_FIRST)
                    isLoading.postValue(true);
                else {
                    isLoadMore.postValue(true);
                }
                page = currentPage + 1;
                isLoading.postValue(true);
                Disposable disposable = preConsumer(apiBookService.getTrendBook(page, PAGE_SIZE).doOnError(handleError()))
                        .subscribe(response ->
                                {
                                    if (currentPage == PAGE_FIRST)
                                        isLoading.postValue(false);
                                    else {
                                        isLoadMore.postValue(false);
                                    }
                                    if (response.data.result.size() < PAGE_SIZE)
                                        isEndPage = true;
                                    currentPage++;
                                    books.postValue(response.data.result);
                                },
                                handleError()
                        );

                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loadDailyBook() {
        if (NetworkConnectReceiver.isConnected()) {
            if (!isEndPage) {
                int page;
                if (currentPage == PAGE_FIRST)
                    isLoading.postValue(true);
                else {
                    isLoadMore.postValue(true);
                }
                page = currentPage + 1;

                Disposable disposable = preConsumer(apiBookService.getDailyBook(page, PAGE_SIZE).doOnError(handleError()))
                        .subscribe(response ->
                                {
                                    if (currentPage == PAGE_FIRST)
                                        isLoading.postValue(false);
                                    else {
                                        isLoadMore.postValue(false);
                                    }
                                    if (response.data.result.size() < PAGE_SIZE)
                                        isEndPage = true;
                                    currentPage++;
                                    books.postValue(response.data.result);
                                },
                                handleError()
                        );

                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loadSelectBook() {
        if (NetworkConnectReceiver.isConnected()) {
            if (!isEndPage) {
                int page;
                if (currentPage == PAGE_FIRST)
                    isLoading.postValue(true);
                else {
                    isLoadMore.postValue(true);
                }
                page = currentPage + 1;
                Disposable disposable = preConsumer(apiBookService.getSelectionBook(page, PAGE_SIZE).doOnError(handleError()))
                        .subscribe(response ->
                                {
                                    if (currentPage == PAGE_FIRST)
                                        isLoading.postValue(false);
                                    else {
                                        isLoadMore.postValue(false);
                                    }
                                    if (response.data.result.size() < PAGE_SIZE)
                                        isEndPage = true;

                                    currentPage++;
                                    books.postValue(response.data.result);
                                },
                                handleError()
                        );

                compositeDisposable.add(disposable);
            }
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loadCategoriesBook(String categories) {
        if (NetworkConnectReceiver.isConnected()) {
            if (!isEndPage) {
                int page;
                if (currentPage == PAGE_FIRST)
                    isLoading.postValue(true);
                else {
                    isLoadMore.postValue(true);
                }
                page = currentPage + 1;
                Disposable disposable = preConsumer(apiBookService.getBookCategories(categories, page, PAGE_SIZE).doOnError(handleError()))
                        .subscribe(response ->
                                {
                                    if (currentPage == PAGE_FIRST)
                                        isLoading.postValue(false);
                                    else {
                                        isLoadMore.postValue(false);
                                    }
                                    if (response.data.result.size() < PAGE_SIZE)
                                        isEndPage = true;
                                    currentPage++;
                                    books.postValue(response.data.result);
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

    @Override
    protected Consumer<Throwable> handleError() {
        isLoadMore.postValue(false);
        return super.handleError();
    }

}
