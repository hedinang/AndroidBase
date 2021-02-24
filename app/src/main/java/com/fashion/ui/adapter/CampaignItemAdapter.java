package com.fashion.ui.adapter;


import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.R;
import com.fashion.core.ui.BaseActivity;
import com.fashion.core.ui.adapter.ClickItemAdapter;
import com.fashion.data.model.Campaign;
import com.fashion.widget.AspectRatioLayout;

import java.util.ArrayList;
import java.util.List;

public class CampaignItemAdapter extends RecyclerView.Adapter<CampaignItemAdapter.ViewHolder> {
    final List<Campaign> campaigns;
    private ClickItemAdapter<Campaign> clickItemAdapter;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    public CampaignItemAdapter(BaseActivity mActivity) {
        campaigns = new ArrayList<>();
        mActivity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
    }

    @NonNull
    @Override
    public CampaignItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_campaign, viewGroup, false);
        return new CampaignItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignItemAdapter.ViewHolder holder, int i) {
        Campaign campaign = campaigns.get(i);
        holder.tvContent.setText(campaign.description);
        holder.tvContent.setTextSize(campaign.fontSize);
        holder.arlLayout.setBackgroundColor(Color.parseColor(campaign.bgColor));
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public void changeData(List<Campaign> items) {
        this.campaigns.clear();
        this.campaigns.addAll(items);
        notifyDataSetChanged();
    }

    public ClickItemAdapter<Campaign> getClickItemAdapter() {
        return clickItemAdapter;
    }

    public void setClickItemAdapter(ClickItemAdapter<Campaign> clickItemAdapter) {
        this.clickItemAdapter = clickItemAdapter;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public AspectRatioLayout arlLayout;
        public TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_book_content);
            arlLayout = itemView.findViewById(R.id.arl_layout);
            arlLayout.setAspectRatio(2, 0.93f);
            arlLayout.setWidthRatioOfParent(0.75f);
            tvContent.setWidth((int) (displayMetrics.widthPixels * 0.75f / 2));
            itemView.setOnClickListener(v -> {
                if (clickItemAdapter != null)
                    clickItemAdapter.onClickItem(campaigns.get(getAdapterPosition()), v);
            });
        }
    }

}
