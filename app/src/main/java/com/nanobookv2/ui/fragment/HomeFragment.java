package com.nanobookv2.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.core.ui.HandleViewModelBasic;
import com.nanobookv2.core.ui.adapter.ClickItemAdapter;
import com.nanobookv2.core.ui.adapter.SubClickItemAdapter;
import com.nanobookv2.data.model.Book;
import com.nanobookv2.data.model.Campaign;
import com.nanobookv2.data.model.Category;
import com.nanobookv2.data.model.Quote;
import com.nanobookv2.ui.adapter.BookHomeItemAdapter;
import com.nanobookv2.ui.adapter.CampaignItemAdapter;
import com.nanobookv2.ui.adapter.CategoryItemAdapter;
import com.nanobookv2.ui.adapter.QuoteItemAdapter;
import com.nanobookv2.ui.dialog.DialogErrorNetwork;
import com.nanobookv2.ui.dialog.DialogLoading;
import com.nanobookv2.ui.view_model.BookMarkViewModel;
import com.nanobookv2.ui.view_model.HomeViewModel;
import com.nanobookv2.utils.Fragments;
import com.nanobookv2.widget.AutoScrollViewPager;
import com.nanobookv2.widget.ItemOffsetDecoration;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import me.relex.circleindicator.CircleIndicator;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends BaseFragment implements HandleViewModelBasic {
    @BindView(R.id.view_pager_quotes)
    AutoScrollViewPager viewPagerQuotes;

    @BindView(R.id.quote_circle_indicator)
    CircleIndicator circleIndicator;
    QuoteItemAdapter quoteItemAdapter;

    @BindView(R.id.rcv_new_book)
    RecyclerView rcvNewBook;
    BookHomeItemAdapter newestBookAdapter;

    @BindView(R.id.rcv_trend_book)
    RecyclerView rcvTrendBook;
    BookHomeItemAdapter trendBookAdapter;

    @BindView(R.id.rcv_daily_book)
    RecyclerView rcvDailyBook;
    BookHomeItemAdapter dailyBookAdapter;

    @BindView(R.id.rcv_select_book)
    RecyclerView rcvSelectBook;
    BookHomeItemAdapter selectBookAdapter;

    @BindView(R.id.rcv_book_categories)
    RecyclerView rcvBookCategories;
    StaggeredGridLayoutManager categoryLayoutManager;
    CategoryItemAdapter categoryItemAdapter;

    @BindView(R.id.rcv_campaign)
    RecyclerView rcvCampaign;
    LinearLayoutManager campaignManager;
    CampaignItemAdapter campaignItemAdapter;

    HomeViewModel homeViewModel;
    BookMarkViewModel bookMarkViewModel;
    ClickItemAdapter<Quote> quoteClickItem;
    ClickItemAdapter<Book> bookClickItemAdapter;
    SubClickItemAdapter<Book> bookSubClickItemAdapter;
    ClickItemAdapter<Category> categoryClickItemAdapter;
    ClickItemAdapter<Campaign> campaignClickItemAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        fragmentComponent.inject(this);

    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        homeViewModel = new ViewModelProvider(this, viewModelProvider).get(HomeViewModel.class);
        bookMarkViewModel = new ViewModelProvider(this, viewModelProvider).get(BookMarkViewModel.class);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        initListener();
        initQuote();
        initNewestBook();
        initTrendBook();
        initDailyBook();
        initSelectBook();
        initCategories();
        initCampaign();
        handleViewModelBase(this.mActivity, homeViewModel);
        homeViewModel.networkInvalid.observe(this, networkInvalid -> {
            if (networkInvalid != null && networkInvalid)
                new DialogErrorNetwork(this::loadData).open(mActivity.getSupportFragmentManager());
        });
        homeViewModel.overViewBook.observe(this, book -> {
            new Handler().postDelayed(() -> {
                if (book != null)
                    Fragments.replace(
                            this.mActivity.getSupportFragmentManager(),
                            R.id.container_main,
                            OverViewBookFragment.newInstance(book),
                            null,
                            true
                    );

                homeViewModel.overViewBook.postValue(null);
            }, 300);
        });
    }

    private void initListener() {
        quoteClickItem = (quote, view) -> {
            homeViewModel.loadOverViewBook(quote.bookId);
        };

        bookClickItemAdapter = (book, view) -> Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_main,
                OverViewBookFragment.newInstance(book),
                null,
                true
        );

        bookSubClickItemAdapter = (book, subView) -> {
            if (book.isBookmark)
                bookMarkViewModel.addBookMark(book.getId());
            else
                bookMarkViewModel.removeBookMark(book.getId());
        };

        categoryClickItemAdapter = (category, view) -> Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_main,
                CategoryDetailFragment.newInstance(category),
                null,
                true
        );

        campaignClickItemAdapter = (campaign, view) -> Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_home,
                CampaignDetailFragment.newInstance(campaign),
                null,
                true
        );
    }

    private void initQuote() {
        quoteItemAdapter = new QuoteItemAdapter(this.mActivity);
        quoteItemAdapter.setClickQuoteItem(quoteClickItem);
        viewPagerQuotes.setAdapter(quoteItemAdapter);
        viewPagerQuotes.startAutoScroll();
        viewPagerQuotes.setInterval(3500);
        viewPagerQuotes.setCycle(true);
        viewPagerQuotes.setStopScrollWhenTouch(true);
        circleIndicator.setViewPager(viewPagerQuotes);

        homeViewModel.quotes.observe(this, quotes -> {
            if (quotes != null && !quotes.isEmpty()) {
                quoteItemAdapter.changeData(quotes);
                circleIndicator.setViewPager(viewPagerQuotes);
            }
        });
    }

    private void initNewestBook() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        newestBookAdapter = new BookHomeItemAdapter(getActivity());
        newestBookAdapter.setSessionManager(homeViewModel.sessionManager);
        newestBookAdapter.setClickItemAdapter(this.bookClickItemAdapter);
        newestBookAdapter.setSubClickItemAdapter(this.bookSubClickItemAdapter);
        rcvNewBook.setAdapter(newestBookAdapter);
        rcvNewBook.setLayoutManager(gridLayoutManager);
        rcvNewBook.setNestedScrollingEnabled(false);
        rcvNewBook.addItemDecoration(new ItemOffsetDecoration(20));
        homeViewModel.newestBook.observe(this, books -> {
            if (books.size() <= 1) {
                newestBookAdapter
                        .setRatioWidthOfRoot(1f)
                        .setHeightRatio(2f)
                        .setWidthRatio(3f);
            } else {
                newestBookAdapter
                        .setRatioWidthOfRoot(0.4f)
                        .setHeightRatio(1.7f)
                        .setWidthRatio(1f);
            }
            newestBookAdapter.changeData(books);
        });
    }

    private void initTrendBook() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        trendBookAdapter = new BookHomeItemAdapter(getActivity());
        trendBookAdapter.setSessionManager(homeViewModel.sessionManager);
        trendBookAdapter.setClickItemAdapter(this.bookClickItemAdapter);
        trendBookAdapter.setSubClickItemAdapter(this.bookSubClickItemAdapter);
        rcvTrendBook.setAdapter(trendBookAdapter);
        rcvTrendBook.setLayoutManager(gridLayoutManager);
        rcvTrendBook.setNestedScrollingEnabled(false);
        rcvTrendBook.addItemDecoration(new ItemOffsetDecoration(20));
        homeViewModel.trendBook.observe(this, books -> {
            if (books.size() <= 1) {
                trendBookAdapter
                        .setRatioWidthOfRoot(1f)
                        .setHeightRatio(2f)
                        .setWidthRatio(3f);
            } else {
                trendBookAdapter
                        .setRatioWidthOfRoot(0.4f)
                        .setHeightRatio(1.7f)
                        .setWidthRatio(1f);
            }
            trendBookAdapter.changeData(books);
        });
    }

    private void initDailyBook() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        dailyBookAdapter = new BookHomeItemAdapter(getActivity());
        dailyBookAdapter.setSessionManager(homeViewModel.sessionManager);
        dailyBookAdapter.setClickItemAdapter(this.bookClickItemAdapter);
        dailyBookAdapter.setSubClickItemAdapter(this.bookSubClickItemAdapter);
        rcvDailyBook.setAdapter(dailyBookAdapter);
        rcvDailyBook.setLayoutManager(gridLayoutManager);
        rcvDailyBook.setNestedScrollingEnabled(false);
        rcvDailyBook.addItemDecoration(new ItemOffsetDecoration(20));
        homeViewModel.dailyBook.observe(this, books -> {
            if (books.size() <= 1) {
                dailyBookAdapter
                        .setRatioWidthOfRoot(1f)
                        .setHeightRatio(2f)
                        .setWidthRatio(3f);
            } else {
                dailyBookAdapter
                        .setRatioWidthOfRoot(0.75f)
                        .setHeightRatio(3f)
                        .setWidthRatio(4f);
            }
            dailyBookAdapter.changeData(books);
        });
    }

    private void initSelectBook() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        selectBookAdapter = new BookHomeItemAdapter(getActivity());
        selectBookAdapter.setSessionManager(homeViewModel.sessionManager);
        selectBookAdapter.setClickItemAdapter(this.bookClickItemAdapter);
        selectBookAdapter.setSubClickItemAdapter(this.bookSubClickItemAdapter);
        rcvSelectBook.setAdapter(selectBookAdapter);
        rcvSelectBook.setLayoutManager(gridLayoutManager);
        rcvSelectBook.setNestedScrollingEnabled(false);
        rcvSelectBook.addItemDecoration(new ItemOffsetDecoration(20));
        homeViewModel.selectedBook.observe(this, books -> {
            if (books.size() <= 1) {
                selectBookAdapter
                        .setRatioWidthOfRoot(1f)
                        .setHeightRatio(2f)
                        .setWidthRatio(3f);
            } else {
                selectBookAdapter
                        .setRatioWidthOfRoot(0.75f)
                        .setHeightRatio(3f)
                        .setWidthRatio(4f);
            }
            selectBookAdapter.changeData(books);
        });
    }

    private void initCategories() {
        categoryLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        categoryItemAdapter = new CategoryItemAdapter();
        categoryItemAdapter.setClickItemAdapter(categoryClickItemAdapter);
        rcvBookCategories.setAdapter(categoryItemAdapter);
        rcvBookCategories.setLayoutManager(categoryLayoutManager);
        rcvBookCategories.setNestedScrollingEnabled(false);
        rcvBookCategories.addItemDecoration(new ItemOffsetDecoration(30, 2));
        homeViewModel.categories.observe(this, categories -> {
            if (categories.size() < 9) {
                categoryLayoutManager.setSpanCount(1);
            } else {
                categoryLayoutManager.setSpanCount(3);
            }
            categoryItemAdapter.changeData(categories);
        });
    }

    private void initCampaign() {
        // nanobook selected
        campaignManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        campaignItemAdapter = new CampaignItemAdapter(mActivity);
        campaignItemAdapter.setClickItemAdapter(campaignClickItemAdapter);
        rcvCampaign.setAdapter(campaignItemAdapter);
        rcvCampaign.setLayoutManager(campaignManager);
        rcvCampaign.setNestedScrollingEnabled(false);
        rcvCampaign.addItemDecoration(new ItemOffsetDecoration(30, LinearLayoutManager.VERTICAL));
        homeViewModel.campaigns.observe(this, campaigns -> {
            campaignItemAdapter.changeData(campaigns);
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        System.out.println("loading data");
        homeViewModel.loadDataHome();
    }

    @Optional
    @OnClick({
            R.id.tv_new_book_see_more,
            R.id.tv_trend_book_see_more,
            R.id.tv_daily_book_see_more,
            R.id.tv_select_book_see_more
    })
    void gotoBookSeeMore(View view) {
        BookMoreFragment bookMoreFragment = null;
        int id = view.getId();
        switch (id) {
            case R.id.tv_new_book_see_more:
                bookMoreFragment = BookMoreFragment.newInstance(R.string.new_book, R.string.new_book_content);
                break;
            case R.id.tv_trend_book_see_more:
                bookMoreFragment = BookMoreFragment.newInstance(R.string.trend_book, R.string.trend_book_content);
                break;
            case R.id.tv_daily_book_see_more:
                bookMoreFragment = BookMoreFragment.newInstance(R.string.daily_book, R.string.daily_book_content);
                break;
            case R.id.tv_select_book_see_more:
                bookMoreFragment = BookMoreFragment.newInstance(R.string.select_book, R.string.select_book_content);
                break;
        }

        if (bookMoreFragment != null)
            Fragments.replace(
                    this.mActivity.getSupportFragmentManager(),
                    R.id.container_home,
                    bookMoreFragment,
                    null,
                    true
            );
    }

    @OnClick(R.id.btn_search)
    void gotoSearch() {
        Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_main,
                SearchFragment.newInstance(),
                null,
                true
        );
    }

    @Override
    public void onDestroy() {
        homeViewModel.dispose();
        bookMarkViewModel.dispose();
        super.onDestroy();
    }
}
