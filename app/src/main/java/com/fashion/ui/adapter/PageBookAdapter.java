package com.fashion.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fashion.R;
import com.fashion.widget.page_curl.CurlPage;
import com.fashion.widget.page_curl.CurlView;

import java.util.List;

public class PageBookAdapter implements CurlView.PageProvider {
    private final LinearLayout itemView;
    private final TextView tvContent;
    private final TextView indexPage;
    private final TextView tvChapterTitle;
    private final List<PageChapter> pageChapters;
    float width;
    float height;
    private PageListener pageListener;

    public PageBookAdapter(Context context, List<PageChapter> pageChapters) {
        this.pageChapters = pageChapters;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = (LinearLayout) inflater.inflate(R.layout.item_page_book, null, true);
        tvContent = itemView.findViewById(R.id.tv_content);
        indexPage = itemView.findViewById(R.id.tv_index_page);
        tvChapterTitle = itemView.findViewById(R.id.tv_chapter_title);
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void setPageListener(PageListener pageListener) {
        this.pageListener = pageListener;
    }

    @Override
    public int getPageCount() {
        return pageChapters.size();
    }


    private Bitmap extractBitmap(View view, int width, int height) {

        if (width > 0 && height > 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        }
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();

        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);

        return bitmap;

    }

    @Override
    public void updatePage(CurlPage page, int width, int height, int index) {
        PageChapter pageChapter = pageChapters.get(index);
        if (pageChapter.isFirst) {
            tvChapterTitle.setText(pageChapter.chapterTitle);
            tvChapterTitle.setVisibility(View.VISIBLE);
        } else
            tvChapterTitle.setVisibility(View.GONE);
        tvContent.setText(pageChapters.get(index).content);
        indexPage.setText(String.valueOf(index + 1));
        if (index == pageChapters.size() - 1) {
            indexPage.setText(R.string.end);
            if (!pageChapters.isEmpty() && pageListener != null) pageListener.onPageEnd();
        }
        itemView.refreshDrawableState();
        itemView.requestLayout();
        Bitmap front = extractBitmap(itemView, width, height);
        page.setTexture(front, CurlPage.SIDE_BOTH);
        page.setColor(Color.argb(10, 255, 255, 255), CurlPage.SIDE_BACK);
    }

    public void changeData(List<PageChapter> pageChapters){
        if (pageChapters != null){
            this.pageChapters.clear();
            this.pageChapters.addAll(pageChapters);
        }
    }

    public void addMore(List<PageChapter> pageChapters){
        if (pageChapters != null){
            this.pageChapters.addAll(pageChapters);
        }
    }

    public void setBackgroundItem(String color){
        itemView.setBackgroundColor(Color.parseColor(color));
    }

    public void setTextColor(String color){
        tvContent.setTextColor(Color.parseColor(color));
        indexPage.setTextSize(Color.parseColor(color));
    }

    public void setTextSize(Float textSize){
        tvContent.setTextSize(textSize);
        indexPage.setTextSize(textSize);
    }


    public static class PageChapter {
        public boolean isFirst;
        public String chapterTitle;
        public String content;
    }

    public interface PageListener {
        void onPageEnd();
    }

}