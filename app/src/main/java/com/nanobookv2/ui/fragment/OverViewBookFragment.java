package com.nanobookv2.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.zxing.common.StringUtils;
import com.nanobookv2.R;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.core.config.NetworkConfig;
import com.nanobookv2.core.ui.BaseFragment;
import com.nanobookv2.data.model.Book;
import com.nanobookv2.ui.view_model.ChapterBookViewModel;
import com.nanobookv2.utils.Fragments;
import com.nanobookv2.widget.AspectRatioImageView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class OverViewBookFragment extends BaseFragment {
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_book_overview)
    Toolbar toolbar;
    @BindView(R.id.tv_book_categories)
    TextView tvBookCategories;
    @BindView(R.id.tv_book_duration)
    TextView tvBookDuration;
    @BindView(R.id.tv_book_short_description)
    TextView tvBookShortDescription;
    @BindView(R.id.tv_book_title)
    TextView tvBookTitle;
    @BindView(R.id.tv_book_author)
    TextView tvBookAuthor;
    @BindView(R.id.tv_book_preview)
    TextView tvBookPreview;
    @BindView(R.id.img_book)
    AspectRatioImageView imgBook;
    @BindView(R.id.iv_alpha)
    AspectRatioImageView ivAlpha;
    @BindView(R.id.img_book_mark)
    ImageView imgBookMark;
    @BindView(R.id.img_favorite)
    ImageView imgFavorite;

    private Book book;
    @Inject
    SessionManager sessionManager;
    ColorMatrixColorFilter filter;

    public static OverViewBookFragment newInstance(Book book) {
        OverViewBookFragment fragment = new OverViewBookFragment();
        fragment.book = book;
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        fragment.filter = new ColorMatrixColorFilter(colorMatrix);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_overview_book;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        fragmentComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        initActionUI();
        initInfoBook();
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int maxScroll = appBarLayout.getTotalScrollRange();
            int offset = Math.abs(verticalOffset);

            if (offset >= maxScroll) {
                toolbar.setBackgroundResource(R.color.colorPrimary);

            } else {
                toolbar.setBackgroundResource(android.R.color.transparent);
            }
        });

    }

    private void initActionUI() {
        PorterDuffColorFilter iconReadingFilter = new PorterDuffColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        Button btnReading = root.findViewById(R.id.btn_reading);
        Drawable iconReading = ContextCompat.getDrawable(mActivity, R.drawable.ic_read_book);
        int h = iconReading.getIntrinsicHeight();
        int w = iconReading.getIntrinsicWidth();
        iconReading.setBounds(0, 0, w, h);
        iconReading.setFilterBitmap(true);
        iconReading.setColorFilter(iconReadingFilter);
        btnReading.setCompoundDrawables(iconReading, null, null, null);

        PorterDuffColorFilter iconListeningFilter = new PorterDuffColorFilter(getResources().getColor(R.color.cobalt), PorterDuff.Mode.SRC_ATOP);
        Button btnListening = root.findViewById(R.id.btn_listening);
        Drawable iconListening = ContextCompat.getDrawable(mActivity, R.drawable.ic_listening);
        iconListening.setBounds(0, 0, w, h);
        iconListening.setFilterBitmap(true);
        iconListening.setColorFilter(iconListeningFilter);
        btnListening.setCompoundDrawables(iconListening, null, null, null);
    }

    private void initInfoBook() {
        tvBookCategories.setVisibility(View.VISIBLE);
        tvBookDuration.setVisibility(View.VISIBLE);
        if (book.bookCategories == null || book.bookCategories.isEmpty()) {
            tvBookCategories.setVisibility(View.GONE);
        }else{
            tvBookCategories.setVisibility(View.VISIBLE);
            tvBookCategories.setText(book.getCategories());
        }
        if (TextUtils.isEmpty(book.subtitle))
            tvBookShortDescription.setVisibility(View.GONE);
        else {
            tvBookShortDescription.setVisibility(View.VISIBLE);
            tvBookShortDescription.setText(book.subtitle);
        }
        tvBookDuration.setText(book.duration + "mins");
        tvBookTitle.setText(book.title);
        tvBookAuthor.setText(book.getAuthorAndYear());
        tvBookPreview.setText(book.preview);
        if (book.image != null) {
            String url = NetworkConfig.URL_BASE + "book/" + book.getId() + "/image/" + book.image.getId() + "?size=thumbnail";
            GlideUrl glideUrl = new GlideUrl(url, () -> {
                Map<String, String> headers = new HashMap<>();
                headers.put(NetworkConfig.HEADER_AUTHENTICATION, "Bearer " + sessionManager.accessToken);
                headers.put(NetworkConfig.HEADER_DEVICE_ID, sessionManager.deviceId);
                return headers;
            });
            Glide
                    .with(this.mActivity)
                    .load(glideUrl)
                    .placeholder(R.drawable.img_place_holder)
                    .into(imgBook);
        }
        imgBook.setColorFilter(filter);
        String color = book.bgColor.replace("#", "#50");
        ivAlpha.setBackgroundColor(Color.parseColor(color));
        if (book.isBookmark == null || !book.isBookmark)
            imgBookMark.setImageResource(R.drawable.ic_empty_light_mark);
        else
            imgBookMark.setImageResource(R.drawable.ic_solid_light_mark);
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @OnClick(R.id.fcd_root)
    void doNot() {

    }

    @OnClick(R.id.btn_back)
    void onBack() {
        this.mActivity.onBackPressed();
    }

    @OnClick(R.id.btn_reading)
    void gotoReadingBook() {
        Fragments.replace(
                this.mActivity.getSupportFragmentManager(),
                R.id.container_main,
                ReadingFragment.newInstance(book),
                null,
                true
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
