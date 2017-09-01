package com.hins.myreader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hins.myreader.R;
import com.hins.myreader.activity.FavouriteArticleActivity;
import com.hins.myreader.modle.Article;

import java.util.List;

/**
 * Created by Hins on 2017/8/31.
 */

public class FavouriteArticleListAdapter extends RecyclerView.Adapter<FavouriteArticleListAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> mArticles;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private TextView mTitle;
        private TextView mAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mTitle = (TextView) itemView.findViewById(R.id.favourite_article_title);
            mAuthor = (TextView) itemView.findViewById(R.id.favourite_article_author);

        }
    }

    public FavouriteArticleListAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.favourite_article_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                } else {

                    Article article = mArticles.get(position);
                    FavouriteArticleActivity.start(mContext, article.data.dateChain.curr);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.mTitle.setText(article.data.title);
        holder.mAuthor.setText(article.data.author);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
