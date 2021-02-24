package com.fashion.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.fashion.core.ResponseBase;
import com.fashion.data.Chapter;
import com.fashion.data.remote.ApiBookService;
import com.fashion.ui.broadcast.NetworkConnectReceiver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.fashion.core.config.NetworkConfig.PAGE_FIRST;
import static com.fashion.core.config.NetworkConfig.PAGE_SIZE;

public class ChapterBookViewModel extends NanoViewModel {
    public static final int ACTION_LOAD_FIRST = 0;
    public static final int ACTION_LOAD_MORE = 1;
    public final MutableLiveData<List<Chapter>> chapters = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoadMore = new MutableLiveData<>();
    public int currentPage = 0;
    public boolean isEndPage = false;
    @Inject
    ApiBookService apiBookService;

    @Inject
    public ChapterBookViewModel() {
        super();
    }

    public void loadChapters(final String bookId) {
        if (NetworkConnectReceiver.isConnected()) {
            if (!isEndPage) {
                int page = currentPage + 1;
                if (currentPage == PAGE_FIRST)
                    isLoading.postValue(true);
                else {
                    isLoadMore.postValue(true);
                }
                Disposable disposable = preConsumer(apiBookService
                        .getChapters(bookId, page, PAGE_SIZE)
                        .doOnError(handleError()))
                        .flatMapIterable(pageChapter -> pageChapter.data.result)
                        .flatMap(item -> preConsumer(apiBookService.getChapterDetails(bookId, item.chapterId)))
                        .toList()
                        .subscribe(response -> {
                                    if (currentPage == PAGE_FIRST)
                                        isLoading.postValue(false);
                                    else {
                                        isLoadMore.postValue(false);
                                    }
                                    List<Chapter> chaptersList = new ArrayList<>();
                                    for (ResponseBase<Chapter> chapterRes : response) {
                                        chaptersList.add(chapterRes.data);
                                    }
                                    currentPage++;
                                    if (chaptersList.size() < PAGE_SIZE)
                                        isEndPage = true;
                                    chapters.postValue(chaptersList);
                                },
                                handleError()
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
