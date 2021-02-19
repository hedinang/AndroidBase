package com.nanobookv2.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

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

@SuppressLint("NonConstantResourceId")
public class CampaignDetailFragment extends BaseFragment implements HandleViewModelBasic {
    @BindView(R.id.fcd_scrollview)
    ObservableScrollView fcdScrollView;
    @BindView(R.id.rcv_book_campaign)
    RecyclerView rcvBookCampaign;
    @BindView(R.id.tv_campaign_title)
    TextView tvCampaignTitle;
    @BindView(R.id.arl_header)
    View arlHeader;
    @Inject
    SessionManager sessionManager;
    BookMoreItemAdapter bookCampaignAdapter;
    Campaign campaign;
    ListBookViewModel listBookViewModel;
    ClickItemAdapter<Book> bookClickItemAdapter;
    SubClickItemAdapter<Book> bookSubClickItemAdapter;
    ObservableScrollView.ScrollViewListener scrollViewListener;

    public static CampaignDetailFragment newInstance(Campaign campaign) {
        CampaignDetailFragment fragment = new CampaignDetailFragment();
        fragment.campaign = campaign;
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_campaign_detail;
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
        initBookCampaign();
        fcdScrollView.setScrollViewListener(scrollViewListener);
        handleViewModelBase(this.mActivity, listBookViewModel);
    }

    @Override
    protected void loadData() {
        super.loadData();
        tvCampaignTitle.setText(this.campaign.description);
        arlHeader.setBackgroundColor(Color.parseColor(campaign.bgColor));
    }

    private void initBookCampaign() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookCampaignAdapter = new BookMoreItemAdapter(getActivity());
        bookCampaignAdapter.setClickItemAdapter(bookClickItemAdapter);
        bookCampaignAdapter.setSubClickItemAdapter(bookSubClickItemAdapter);
        bookCampaignAdapter.setSessionManager(sessionManager);
        rcvBookCampaign.setAdapter(bookCampaignAdapter);
        rcvBookCampaign.setLayoutManager(gridLayoutManager);
        rcvBookCampaign.setNestedScrollingEnabled(false);
        rcvBookCampaign.addItemDecoration(new DividerItemDecoration(this.mContext, DividerItemDecoration.VERTICAL));
        listBookViewModel.books.observe(this, books -> bookCampaignAdapter.addMoreData(books));

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
    void doNot(){

    }

    @OnClick(R.id.btn_back)
    void onBack(){
        this.mActivity.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listBookViewModel.dispose();
    }
}
