package com.fashion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;
    private int orientation;
    public ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
        this.orientation = LinearLayoutManager.HORIZONTAL;
    }

    public ItemOffsetDecoration(int itemOffset, int orientation) {
        mItemOffset = itemOffset;
        this.orientation = orientation;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        outRect.bottom = mItemOffset;
        outRect.top = mItemOffset;
        if (orientation == LinearLayoutManager.HORIZONTAL){
            if (position == 0){
                outRect.left = 0;
                outRect.right = mItemOffset;
            }else{
                outRect.left = mItemOffset;
                outRect.right = mItemOffset;
            }
        }else if (orientation == LinearLayoutManager.VERTICAL){
            outRect.left = 0;
            outRect.right = 0;
        }else{
            outRect.top = 0;
            outRect.left = 0;
            outRect.right = mItemOffset;
        }
    }
}