package com.hins.myreader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hins.myreader.R;
import com.hins.myreader.activity.ZhihuDetailStoryActivity;
import com.hins.myreader.modle.ZhihuStoryTitle;

import java.util.List;

/**
 * Created by Hins on 2017/8/28.
 */

public class ZhihuStoryTitleAdapter extends RecyclerView.Adapter<ZhihuStoryTitleAdapter.ViewHolder> {

    private Context mContext;
    private List<ZhihuStoryTitle> mTitles;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView titleTextView;
        private ImageView titleImage;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            titleTextView = (TextView) itemView.findViewById(R.id.zhihu_story_title);
            titleImage = (ImageView) itemView.findViewById(R.id.zhihu_story_image);
        }
    }

    public ZhihuStoryTitleAdapter(List<ZhihuStoryTitle> titles) {
        mTitles = titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.zhihu_story_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ZhihuStoryTitle title = mTitles.get(position);
                ZhihuDetailStoryActivity.start(mContext, title.getId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ZhihuStoryTitle title = mTitles.get(position);
        holder.titleTextView.setText(title.getTitle());
        Glide.with(mContext).load(title.getUrl()).into(holder.titleImage);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }
}
