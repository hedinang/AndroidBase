package com.fashion.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class ObservableScrollView extends NestedScrollView {

    private ScrollViewListener scrollViewListener;

    public ObservableScrollView(@NonNull Context context) {
        super(context);
    }

    public ObservableScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        View view= getChildAt(getChildCount() - 1);
        int diff = view.getBottom() - getHeight() - getScrollY();
        if (diff == 0) { // if diff is zero, then the bottom has been reached
            if (scrollViewListener != null) {
                scrollViewListener.onScrollEnded(this, x, y, oldx, oldy);
            }
        }
        super.onScrollChanged(x, y, oldx, oldy);
    }

    public interface ScrollViewListener {
        void onScrollEnded(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}