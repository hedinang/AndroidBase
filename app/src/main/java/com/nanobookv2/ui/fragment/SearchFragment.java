package com.nanobookv2.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nanobookv2.R;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.core.ui.HandleViewModelBasic;
import com.nanobookv2.core.ui.adapter.ClickItemAdapter;
import com.nanobookv2.core.ui.adapter.SubClickItemAdapter;
import com.nanobookv2.data.model.Book;
import com.nanobookv2.data.model.Campaign;
import com.nanobookv2.ui.adapter.BookMoreItemAdapter;
import com.nanobookv2.ui.view_model.ListBookViewModel;
import com.nanobookv2.utils.Fragments;
import com.nanobookv2.widget.ObservableScrollView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

@SuppressLint("NonConstantResourceId")
public class SearchFragment extends BaseFragment implements HandleViewModelBasic {
    @BindView(R.id.fs_scrollview)
    ObservableScrollView fsScrollView;
    @BindView(R.id.rcv_book_search)
    RecyclerView rcvBookSearch;
    BookMoreItemAdapter bookSearchItemAdapter;
    @BindView(R.id.edit_search)
    EditText editSearch;

    @Inject
    SessionManager sessionManager;
    Campaign campaign;
    ListBookViewModel listBookViewModel;
    ClickItemAdapter<Book> bookClickItemAdapter;
    SubClickItemAdapter<Book> bookSubClickItemAdapter;
    ObservableScrollView.ScrollViewListener scrollViewListener;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
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
        initListener();
        initBookSearch();
        fsScrollView.setScrollViewListener(scrollViewListener);
        handleViewModelBase(this.mActivity, listBookViewModel);
    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    private void initBookSearch() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookSearchItemAdapter = new BookMoreItemAdapter(getActivity());
        bookSearchItemAdapter.setClickItemAdapter(bookClickItemAdapter);
        bookSearchItemAdapter.setSubClickItemAdapter(bookSubClickItemAdapter);
        bookSearchItemAdapter.setSessionManager(sessionManager);
        rcvBookSearch.setAdapter(bookSearchItemAdapter);
        rcvBookSearch.setLayoutManager(linearLayoutManager);
        rcvBookSearch.setNestedScrollingEnabled(false);
        rcvBookSearch.addItemDecoration(new DividerItemDecoration(this.mContext, DividerItemDecoration.VERTICAL));
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

        scrollViewListener = (ObservableScrollView scrollView, int x, int y, int oldx, int oldy) -> {};
    }

    @OnClick(R.id.fcd_root)
    void doNot() {

    }

    @OnClick(R.id.btn_cancel)
    void onBack() {
        if (editSearch.isFocused()){
            hideSoftKeyboard(this.mActivity);
            new Handler().postDelayed(() -> {
                this.mActivity.onBackPressed();
            }, 450);
        }else{
            this.mActivity.onBackPressed();
        }
    }

    @OnEditorAction(R.id.edit_search)
    boolean onActionSearch(int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(mActivity, "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listBookViewModel.dispose();
    }
}
