package com.fashion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.R;
import com.fashion.core.ui.adapter.ClickItemAdapter;
import com.fashion.data.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {
    final List<Category> categories;
    private ClickItemAdapter<Category> clickItemAdapter;


    public CategoryItemAdapter(){
        categories = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvContent.setText(categories.get(i).nameVi);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void changeData(List<Category> items) {
        this.categories.clear();
        this.categories.addAll(items);
        notifyDataSetChanged();
    }

    public ClickItemAdapter<Category> getClickItemAdapter() {
        return clickItemAdapter;
    }

    public void setClickItemAdapter(ClickItemAdapter<Category> clickItemAdapter) {
        this.clickItemAdapter = clickItemAdapter;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView tvContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_book_content);
            itemView.setOnClickListener(v -> {
                if (clickItemAdapter != null)
                    clickItemAdapter.onClickItem(categories.get(getAdapterPosition()), v);
            });
        }
    }
}
