package com.nanobookv2.ui.view_model;

import androidx.lifecycle.MutableLiveData;

import com.nanobookv2.data.model.Book;
import com.nanobookv2.data.model.Campaign;
import com.nanobookv2.data.model.Category;
import com.nanobookv2.data.model.Quote;
import com.nanobookv2.data.remote.ApiBookService;
import com.nanobookv2.ui.broadcast.NetworkConnectReceiver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import static com.nanobookv2.core.config.NetworkConfig.PAGE_FIRST;
import static com.nanobookv2.core.config.NetworkConfig.PAGE_SIZE;

public class HomeViewModel extends NanoViewModel {
    public static final int ACTION_LOAD_ALL = 0;
    public static final int ACTION_LOAD_MORE = 1;
    public static final int ACTION_ADD_MARK_BOOK = 2;
    public static final int ACTION_LOAD_OVERVIEW_BOOK = 3;
    public final MutableLiveData<Book> overViewBook = new MutableLiveData<>();
    public final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public final MutableLiveData<List<Quote>> quotes = new MutableLiveData<>();
    public final MutableLiveData<List<Campaign>> campaigns = new MutableLiveData<>();
    public final MutableLiveData<List<Book>> newestBook = new MutableLiveData<>();
    public final MutableLiveData<List<Book>> trendBook = new MutableLiveData<>();
    public final MutableLiveData<List<Book>> dailyBook = new MutableLiveData<>();
    public final MutableLiveData<List<Book>> selectedBook = new MutableLiveData<>();
    @Inject
    ApiBookService apiBookService;

    @Inject
    public HomeViewModel() {
        super();
    }

    public void loadDataHome() {
        if (NetworkConnectReceiver.isConnected()) {
            isLoading.postValue(true);
            Disposable disposable = preConsumer(
                    Single.zip(
                            apiBookService.getQuotes(),
                            apiBookService.getNewestBook(PAGE_FIRST +1, PAGE_SIZE),
                            apiBookService.getTrendBook(PAGE_FIRST + 1, PAGE_SIZE),
                            apiBookService.getDailyBook(PAGE_FIRST + 1, PAGE_SIZE),
                            apiBookService.getSelectionBook(PAGE_FIRST + 1, PAGE_SIZE),
                            apiBookService.getCategories(),
                            apiBookService.getCampaign(),
                            (quotesPage, newestBookPage, trendBookPage, dailyBookPage, selectBookPage, categoryPage, campaignPage) -> {
                                DataZip dataZip = new DataZip();
                                dataZip.quotes = quotesPage.data.result;
                                dataZip.newestBook = newestBookPage.data.result;
                                dataZip.trendBooks = trendBookPage.data.result;
                                dataZip.dailyBooks = dailyBookPage.data.result;
                                dataZip.selectBooks = selectBookPage.data.result;
                                dataZip.categories = categoryPage.data.result;
                                dataZip.campaigns = campaignPage.result;
                                return dataZip;
                            }))
                    .subscribe(response -> {
                                isLoading.postValue(false);
                                quotes.postValue(response.quotes);
                                newestBook.postValue(response.newestBook);
                                trendBook.postValue(response.trendBooks);
                                dailyBook.postValue(response.dailyBooks);
                                selectedBook.postValue(response.selectBooks);
                                categories.postValue(response.categories);
                                campaigns.postValue(response.campaigns);
                            },
                            handleError());
            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    public void loadOverViewBook(String bookId) {
        if (NetworkConnectReceiver.isConnected()) {
            isLoading.postValue(true);
            Disposable disposable = preConsumer(apiBookService.findBookById(bookId))
                    .subscribe(response ->
                            {
                                overViewBook.postValue(response.data);
                            },
                            handleError(),
                            onCompleted()
                    );

            compositeDisposable.add(disposable);
        } else {
            networkInvalid.postValue(true);
        }
    }

    private static class DataZip {
        public List<Quote> quotes;
        public List<Book> newestBook;
        public List<Book> trendBooks;
        public List<Book> dailyBooks;
        public List<Book> selectBooks;
        public List<Category> categories;
        public List<Campaign> campaigns;
    }

}
