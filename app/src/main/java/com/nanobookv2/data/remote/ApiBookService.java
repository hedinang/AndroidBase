package com.nanobookv2.data.remote;

import com.nanobookv2.core.Page;
import com.nanobookv2.core.ResponseBase;
import com.nanobookv2.data.Chapter;
import com.nanobookv2.data.model.Book;
import com.nanobookv2.data.model.BookId;
import com.nanobookv2.data.model.BookMarkDTO;
import com.nanobookv2.data.model.Campaign;
import com.nanobookv2.data.model.Category;
import com.nanobookv2.data.model.Quote;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiBookService {
    /*-------------- AUTH API --------------*/
    @GET("quotes")
    Single<ResponseBase<Page<Quote>>> getQuotes();

    @GET("books?order=newest")
    Single<ResponseBase<Page<Book>>> getNewestBook(@Query("page") int page, @Query("perPage") int size);

    @GET("book/{id}")
    Observable<ResponseBase<Book>> findBookById(@Path("id") String id);

    @GET("nanobookdailies?attach=all")
    Single<ResponseBase<Page<Book>>> getDailyBook(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookfavorites?attach=all")
    Single<ResponseBase<Page<Book>>> getTrendBook(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookexperts?attach=all")
    Single<ResponseBase<Page<Book>>> getSelectionBook(@Query("page") int page, @Query("perPage") int size);

    @GET("categories")
    Single<ResponseBase<Page<Category>>> getCategories();

    @GET("nanobookselectionsettings")
    Single<Page<Campaign>> getCampaign();

    @GET("book/{bookId}/chapters")
    Observable<ResponseBase<Page<Chapter>>> getChapters(@Path("bookId") String bookId, @Query("page") int page, @Query("perPage") int size);

    @GET("book/{bookId}/chapter/{chapterId}")
    Observable<ResponseBase<Chapter>> getChapterDetails(@Path("bookId") String bookId, @Path("chapterId") int chapterId);

    @GET("nanobookselectiontoptens")
    Single<ResponseBase<Page<BookId>>> getBookTopTens(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookselectionnewyorktimes")
    Single<ResponseBase<Page<BookId>>> getBookNewYorkTimes(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookselectionbeststories")
    Single<ResponseBase<Page<BookId>>> getBookBestStories(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookselectionstartups")
    Single<ResponseBase<Page<BookId>>> getBookStartUps(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookselectionpeaces")
    Single<ResponseBase<Page<BookId>>> getBookPeaces(@Query("page") int page, @Query("perPage") int size);

    @GET("nanobookselectionbestsellers")
    Single<ResponseBase<Page<BookId>>> getBookBestSeller(@Query("page") int page, @Query("perPage") int size);

    @GET("books")
    Observable<ResponseBase<Page<Book>>> getBookCategories(@Query("categories") String categories, @Query("page") int page, @Query("perPage") int size);

    @POST("user/{userId}/bookmarks")
    Single<ResponseBase<BookMarkDTO.Response>> addBookMark(@Path("userId") String userId, @Body BookMarkDTO.Request request);

    @DELETE("user/{userId}/bookmarks/{bookId}")
    Single<ResponseBase<BookMarkDTO.Response>> removeBookMark(@Path("userId") String userId, @Path("bookId") String bookId);

}
