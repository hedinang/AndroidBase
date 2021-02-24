package com.fashion.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.R;
import com.fashion.core.SessionManager;
import com.fashion.core.ui.BaseFragment;
import com.fashion.core.ui.HandleViewModelBasic;
import com.fashion.core.ui.adapter.ClickItemAdapter;
import com.fashion.core.ui.adapter.SubClickItemAdapter;
import com.fashion.data.model.Book;
import com.fashion.ui.adapter.BookMoreItemAdapter;
import com.fashion.ui.view_model.ListBookViewModel;
import com.fashion.utils.Fragments;
import com.fashion.widget.CustomScrollListener;
import com.fashion.widget.ObservableScrollView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class BookMoreFragment extends BaseFragment implements HandleViewModelBasic {
    @BindView(R.id.fbm_scrollview)
    ObservableScrollView fbmScrollView;
    @BindView(R.id.rcv_book_more)
    RecyclerView rcvBookMore;
    @BindView(R.id.tv_more_title)
    TextView tvMoreTitle;
    @BindView(R.id.tv_more_sub_title)
    TextView tvMoreSubTitle;
    BookMoreItemAdapter bookMoreItemAdapter;
    private int titleStringId;
    private int subTitleStringId;
    @Inject
    SessionManager sessionManager;
    ListBookViewModel listBookViewModel;
    ClickItemAdapter<Book> bookClickItemAdapter;
    SubClickItemAdapter<Book> bookSubClickItemAdapter;
    ObservableScrollView.ScrollViewListener scrollViewListener;
    private BookMoreFragment() {
    }

    public static BookMoreFragment newInstance(@StringRes int titleStringId, @StringRes int subTitleStringId) {
        BookMoreFragment fragment = new BookMoreFragment();
        fragment.titleStringId = titleStringId;
        fragment.subTitleStringId = subTitleStringId;
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_book_more;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        fragmentComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        listBookViewModel = new ViewModelProvider(this, viewModelProvider).get(ListBookViewModel.class);
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        tvMoreTitle.setText(titleStringId);
        tvMoreSubTitle.setText(subTitleStringId);
        initListener();
        initBookMore();
        fbmScrollView.setScrollViewListener(scrollViewListener);
        handleViewModelBase(this.mActivity, listBookViewModel);
    }

    @Override
    protected void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            if (listBookViewModel.books.getValue() == null || listBookViewModel.books.getValue().isEmpty())
                loadBookMore();
        }, 300);
    }

    private void loadBookMore(){
        switch (titleStringId) {
            case R.string.new_book:
                listBookViewModel.loadNewestBook();
                break;
            case R.string.trend_book:
                listBookViewModel.loadTrendBook();
                break;
            case R.string.daily_book:
                listBookViewModel.loadDailyBook();
                break;
            case R.string.select_book:
                listBookViewModel.loadSelectBook();
                break;
        }
    }

    private void initListener(){
        bookClickItemAdapter = (book, view) -> Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_main,
                OverViewBookFragment.newInstance(book),
                null,
                true
        );

        bookSubClickItemAdapter = (book, subView) -> {

        };

        scrollViewListener = (ObservableScrollView scrollView, int x, int y, int oldx, int oldy) -> loadBookMore();
    }
    private void initBookMore() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookMoreItemAdapter = new BookMoreItemAdapter(getActivity());
        bookMoreItemAdapter.setClickItemAdapter(bookClickItemAdapter);
        bookMoreItemAdapter.setSubClickItemAdapter(bookSubClickItemAdapter);
        bookMoreItemAdapter.setSessionManager(sessionManager);
        rcvBookMore.setAdapter(bookMoreItemAdapter);
        rcvBookMore.setLayoutManager(gridLayoutManager);
        rcvBookMore.setNestedScrollingEnabled(false);
        rcvBookMore.addOnScrollListener(new CustomScrollListener());
        rcvBookMore.addItemDecoration(new DividerItemDecoration(this.mContext, DividerItemDecoration.VERTICAL));
        listBookViewModel.books.observe(this, books -> bookMoreItemAdapter.addMoreData(books));
    }

    @OnClick(R.id.fcd_root)
    void doNot() {

    }

    @OnClick(R.id.btn_back)
    void onBack() {
        this.mActivity.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listBookViewModel.dispose();
    }
}
