package com.fashion.ui.view_model;

import com.fashion.data.remote.ApiBookService;

import javax.inject.Inject;

public class CampaignViewModel extends NanoViewModel {
    public static final int ACTION_LOAD_FIRST = 0;
    public static final int ACTION_LOAD_MORE = 1;
    public static final int ACTION_ADD_MARK_BOOK = 2;

    @Inject
    ApiBookService apiBookService;

    @Inject
    public CampaignViewModel(){
        super();
    }


}
