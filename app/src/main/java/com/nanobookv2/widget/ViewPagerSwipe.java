package com.nanobookv2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by diepnv.hust@gmail.com on 21/11/2020
 */

public class ViewPagerSwipe extends ViewPager {

    private boolean ableSwipe;

    public ViewPagerSwipe(Context context) {
        super(context);
    }

    public ViewPagerSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ableSwipe = true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.ableSwipe) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.ableSwipe) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setAbleSwipe(boolean ableSwipe) {
        this.ableSwipe = ableSwipe;
    }
}
