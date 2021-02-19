package com.nanobookv2.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.core.ui.adapter.ClickItemAdapter;
import com.nanobookv2.data.model.Quote;

import java.util.ArrayList;
import java.util.List;

public class QuoteItemAdapter extends androidx.viewpager.widget.PagerAdapter {
    private final BaseActivity activity;
    private final List<Quote> quotes;
    private ClickItemAdapter<Quote> clickQuoteItem;
    public QuoteItemAdapter(BaseActivity activity) {
        this.activity = activity;
        quotes = new ArrayList<>();
    }

    public void setClickQuoteItem(ClickItemAdapter<Quote> clickQuoteItem) {
        this.clickQuoteItem = clickQuoteItem;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Quote quote = quotes.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.item_quote, container, false);
        LinearLayout bgRootQuote = viewItem.findViewById(R.id.bg_root_quote);
        TextView tvQuoteContent = viewItem.findViewById(R.id.tv_quotes_content);
        TextView tvQuoteAuthor = viewItem.findViewById(R.id.tv_quotes_author);
        TextView btnReadBook = viewItem.findViewById(R.id.btn_read_book);
        btnReadBook.setOnClickListener((v)->{
            if (clickQuoteItem!=null) clickQuoteItem.onClickItem(quote, viewItem);
        });
        tvQuoteContent.setText(quote.quote);
        tvQuoteAuthor.setText(quote.author);
        bgRootQuote.setBackgroundColor(Color.parseColor(quote.bgColor));
        ((ViewPager) container).addView(viewItem);
        return viewItem;
    }

    public void changeData(List<Quote> quotes) {
        this.quotes.clear();
        if (quotes != null) {
            this.quotes.addAll(quotes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return quotes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}