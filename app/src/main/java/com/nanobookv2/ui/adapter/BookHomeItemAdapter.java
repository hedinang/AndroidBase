package com.nanobookv2.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.nanobookv2.R;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.core.config.NetworkConfig;
import com.nanobookv2.core.ui.adapter.ClickItemAdapter;
import com.nanobookv2.core.ui.adapter.SubClickItemAdapter;
import com.nanobookv2.data.model.Book;
import com.nanobookv2.widget.AspectRatioLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by diepnv.hust@gmail.com on 22/11/2020
 */

public class BookHomeItemAdapter extends RecyclerView.Adapter<BookHomeItemAdapter.ViewHolder> {

    Context context;
    final List<Book> books;
    /*setting view item*/
    private float ratioWidthOfRoot;
    private float widthRatio;
    private float heightRatio;

    SessionManager sessionManager;
    ColorMatrixColorFilter filter;
    private ClickItemAdapter<Book> clickItemAdapter;
    private SubClickItemAdapter<Book> subClickItemAdapter;

    public BookHomeItemAdapter(Context context) {
        this.context = context;
        this.books = new ArrayList<>();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(colorMatrix);
    }

    public ClickItemAdapter<Book> getClickItemAdapter() {
        return clickItemAdapter;
    }

    public void setClickItemAdapter(ClickItemAdapter<Book> clickItemAdapter) {
        this.clickItemAdapter = clickItemAdapter;
    }

    public SubClickItemAdapter<Book> getSubClickItemAdapter() {
        return subClickItemAdapter;
    }

    public void setSubClickItemAdapter(SubClickItemAdapter<Book> subClickItemAdapter) {
        this.subClickItemAdapter = subClickItemAdapter;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Book book = books.get(position);
        if (book.image != null) {
            String url = NetworkConfig.URL_BASE + "book/" + book.getId() + "/image/" + book.image.getId() + "?size=thumbnail";
            GlideUrl glideUrl = new GlideUrl(url, () -> {
                Map<String, String> headers = new HashMap<>();
                headers.put(NetworkConfig.HEADER_AUTHENTICATION, "Bearer " + sessionManager.accessToken);
                headers.put(NetworkConfig.HEADER_DEVICE_ID, sessionManager.deviceId);
                return headers;
            });
            Glide
                    .with(context)
                    .load(glideUrl)
                    .placeholder(R.drawable.img_place_holder)
                    .into(holder.ivBook);
        }
        holder.ivBook.setColorFilter(filter);
        String color = book.bgColor.replace("#", "#50");
        holder.vAlpha.setBackgroundColor(Color.parseColor(color));
        holder.tvAuthor.setText(book.author);
        holder.tvAudioDuration.setText(book.duration + " phut");
        holder.tvBookName.setText(book.title);
        if (book.isBookmark == null || !book.isBookmark)
            holder.ivAddBook.setImageResource(R.drawable.ic_empty_dark_mark);
        else
            holder.ivAddBook.setImageResource(R.drawable.ic_solid_dark_mark);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public BookHomeItemAdapter setRatioWidthOfRoot(float ratioWidthOfRoot) {
        this.ratioWidthOfRoot = ratioWidthOfRoot;
        return this;
    }


    public BookHomeItemAdapter setWidthRatio(float widthRatio) {
        this.widthRatio = widthRatio;
        return this;
    }

    public BookHomeItemAdapter setHeightRatio(float heightRatio) {
        this.heightRatio = heightRatio;
        return this;
    }

    public void changeData(List<Book> items) {
        this.books.clear();
        this.books.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookName, tvAuthor, tvAudioDuration;
        public ImageView ivBook, ivAddBook;
        public AspectRatioLayout arlLayout;
        public View vAlpha;

        public ViewHolder(View view) {
            super(view);
            arlLayout = view.findViewById(R.id.arl_layout);
            arlLayout.setWidthRatioOfParent(ratioWidthOfRoot);
            arlLayout.setAspectRatio(widthRatio, heightRatio);

            ivBook = view.findViewById(R.id.image_book);
            ivAddBook = view.findViewById(R.id.btn_add_book);
            tvBookName = view.findViewById(R.id.tv_book_name);
            tvAuthor = view.findViewById(R.id.tv_author);
            tvAudioDuration = view.findViewById(R.id.tv_audio_duration);
            vAlpha = view.findViewById(R.id.v_alpha);
            if (widthRatio > heightRatio) {
                tvBookName.setMaxLines(2);
                tvBookName.setMinLines(2);
            }

            view.setOnClickListener(v -> {
                if (clickItemAdapter != null)
                    clickItemAdapter.onClickItem(books.get(getAdapterPosition()), v);
            });

            ivAddBook.setOnClickListener(v -> {
                if (subClickItemAdapter != null)
                    subClickItemAdapter.onSubClickItem(books.get(getAdapterPosition()), v);
            });
        }
    }

}
