package com.nanobookv2.ui.fragment;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.ui.adapter.BookMoreItemAdapter;
import com.nanobookv2.ui.adapter.LibraryItemAdapter;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class LibraryFragment extends BaseFragment {
    @BindView(R.id.rcv_book_library)
    RecyclerView rcvBookLibrary;
    LibraryItemAdapter libraryItemAdapter;

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_library;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
    }

    @Override
    protected void loadData() {
        super.loadData();
        initBookLibrary();
    }

    private void initBookLibrary() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        libraryItemAdapter = new LibraryItemAdapter(getActivity());
        rcvBookLibrary.setAdapter(libraryItemAdapter);
        rcvBookLibrary.setLayoutManager(gridLayoutManager);
        rcvBookLibrary.setNestedScrollingEnabled(false);
        rcvBookLibrary.addItemDecoration(new DividerItemDecoration(this.mContext, DividerItemDecoration.VERTICAL));
    }


    @OnClick(R.id.fcd_root)
    void doNot(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
