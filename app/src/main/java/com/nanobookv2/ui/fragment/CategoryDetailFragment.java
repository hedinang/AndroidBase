package com.nanobookv2.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nanobookv2.R;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.core.ui.HandleViewModelBasic;
import com.nanobookv2.core.ui.adapter.ClickItemAdapter;
import com.nanobookv2.core.ui.adapter.SubClickItemAdapter;
import com.nanobookv2.data.model.Book;
import com.nanobookv2.data.model.Category;
import com.nanobookv2.ui.adapter.BookMoreItemAdapter;
import com.nanobookv2.ui.dialog.DialogLoading;
import com.nanobookv2.ui.view_model.ListBookViewModel;
import com.nanobookv2.ui.view_model.NanoViewModel;
import com.nanobookv2.utils.Fragments;
import com.nanobookv2.widget.ObservableScrollView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nanobookv2.core.config.NetworkConfig.PAGE_FIRST;
import static com.nanobookv2.core.config.NetworkConfig.PAGE_SIZE;

@SuppressLint("NonConstantResourceId")
public class CategoryDetailFragment extends BaseFragment implements HandleViewModelBasic {
    @BindView(R.id.fbc_scrollview)
    ObservableScrollView fbcScrollView;
    @BindView(R.id.rcv_book_categories)
    RecyclerView rcvBookCategory;
    @BindView(R.id.tv_category_title)
    TextView tvCategoryTitle;
    @BindView(R.id.tv_category_quotes)
    TextView tvCategoryQuotes;
    BookMoreItemAdapter bookCategoryItemAdapter;
    Category category;
    @Inject
    SessionManager sessionManager;
    ListBookViewModel listBookViewModel;
    ClickItemAdapter<Book> bookClickItemAdapter;
    SubClickItemAdapter<Book> bookSubClickItemAdapter;
    ObservableScrollView.ScrollViewListener scrollViewListener;

    private CategoryDetailFragment() {
    }

    public static CategoryDetailFragment newInstance(Category category) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.category = category;
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_book_category;
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
        tvCategoryTitle.setText(category.nameVi);
        tvCategoryQuotes.setText(category.nameEn);
        initListener();
        initBookMore();
        fbcScrollView.setScrollViewListener(scrollViewListener);
        handleViewModelBase(this.mActivity, listBookViewModel);
    }

    @Override
    protected void loadData() {
        super.loadData();
        new Handler().postDelayed(() -> {
            if (listBookViewModel.books.getValue() == null || listBookViewModel.books.getValue().isEmpty())
                listBookViewModel.loadCategoriesBook(String.valueOf(category.id));
        }, 300);
    }

    private void initListener() {
        bookClickItemAdapter = (book, view) -> Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_main,
                OverViewBookFragment.newInstance(book),
                null,
                true
        );

        bookSubClickItemAdapter = (book, subView) -> {

        };

        scrollViewListener = (ObservableScrollView scrollView, int x, int y, int oldx, int oldy) -> {
            listBookViewModel.loadCategoriesBook(String.valueOf(category.id));
        };
    }

    private void initBookMore() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookCategoryItemAdapter = new BookMoreItemAdapter(getActivity());
        bookCategoryItemAdapter.setClickItemAdapter(bookClickItemAdapter);
        bookCategoryItemAdapter.setSubClickItemAdapter(bookSubClickItemAdapter);
        bookCategoryItemAdapter.setSessionManager(sessionManager);
        rcvBookCategory.setAdapter(bookCategoryItemAdapter);
        rcvBookCategory.setLayoutManager(gridLayoutManager);
        rcvBookCategory.setNestedScrollingEnabled(false);
        rcvBookCategory.addItemDecoration(new DividerItemDecoration(this.mContext, DividerItemDecoration.VERTICAL));
        listBookViewModel.books.observe(this, books -> bookCategoryItemAdapter.addMoreData(books));
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
