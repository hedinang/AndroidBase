package com.nanobookv2.ui.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nanobookv2.R;
import com.nanobookv2.core.SessionManager;
import com.nanobookv2.data.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diepnv.hust@gmail.com on 30/11/2020
 */

public class LibraryItemAdapter extends RecyclerView.Adapter<LibraryItemAdapter.ViewHolder> {

    Context context;
    final List<Book> books;
    /*setting view item*/
    SessionManager sessionManager;
    ColorMatrixColorFilter filter;

    public LibraryItemAdapter(Context context) {
        this.context = context;
        this.books = new ArrayList<>();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(colorMatrix);
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_library, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        Book book = books.get(position);
//        if (book.image != null) {
//            String url = NetworkConfig.URL_BASE + "book/" + book.id + "/image/" + book.image.id + "?size=thumbnail";
//            GlideUrl glideUrl = new GlideUrl(url, () -> {
//                Map<String, String> headers = new HashMap<>();
//                headers.put(NetworkConfig.HEADER_AUTHENTICATION, "Bearer " + sessionManager.accessToken);
//                headers.put(NetworkConfig.HEADER_DEVICE_ID, sessionManager.deviceId);
//                return headers;
//            });
//            Glide
//                    .with(context)
//                    .load(glideUrl)
//                    .placeholder(R.drawable.image_welcome_1)
//                    .into(holder.ivBook);
//        }
//        holder.ivBook.setColorFilter(filter);
//        String color = book.bgColor.replace("#", "#50");
//        holder.vAlpha.setBackgroundColor(Color.parseColor(color));
//        holder.tvAuthor.setText(book.author);
//        holder.tvAudioDuration.setText(book.duration + " phut");
//        holder.tvBookName.setText(book.title);
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public void changeData(List<Book> items) {
        this.books.clear();
        this.books.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookName, tvAuthor, tvAudioDuration;
        public ImageView ivBook, ivAddBook;
        public View vAlpha;

        public ViewHolder(View view) {
            super(view);

        }
    }
}
