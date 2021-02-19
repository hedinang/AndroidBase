package com.nanobookv2.ui.adapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by diepnv.hust@gmail.com on 30/11/2020
 */

public class BookMoreItemAdapter extends RecyclerView.Adapter<BookMoreItemAdapter.ViewHolder> {

    Context context;
    final List<Book> books;
    /*setting view item*/
    SessionManager sessionManager;
    ColorMatrixColorFilter filter;
    private ClickItemAdapter<Book> clickItemAdapter;
    private SubClickItemAdapter<Book> subClickItemAdapter;
    public BookMoreItemAdapter(Context context) {
        this.context = context;
        this.books = new ArrayList<>();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(colorMatrix);
    }

    public void setClickItemAdapter(ClickItemAdapter<Book> clickItemAdapter) {
        this.clickItemAdapter = clickItemAdapter;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_more, parent, false);

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
        holder.tvAuthor.setText(book.getAuthorAndYear());
        holder.tvAudioDuration.setText(book.duration + " phút");
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

    public void changeData(List<Book> items) {
        this.books.clear();
        this.books.addAll(items);
        notifyDataSetChanged();
    }

    public void addMoreData(List<Book> items) {
        int offset = books.size();
        this.books.addAll(items);
        notifyItemRangeChanged(offset, items.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookName, tvAuthor, tvAudioDuration;
        public ImageView ivBook, ivAddBook;
        public View vAlpha;

        public ViewHolder(View view) {
            super(view);
            ivBook = view.findViewById(R.id.img_book);
            ivAddBook = view.findViewById(R.id.img_book_mark);
            tvBookName = view.findViewById(R.id.tv_book_title);
            tvAuthor = view.findViewById(R.id.tv_book_author);
            tvAudioDuration = view.findViewById(R.id.tv_book_duration);
            vAlpha = view.findViewById(R.id.v_alpha);

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
