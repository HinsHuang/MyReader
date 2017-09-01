package com.hins.myreader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hins.myreader.R;
import com.hins.myreader.adapter.FavouriteArticleListAdapter;
import com.hins.myreader.base.BaseFragment;
import com.hins.myreader.modle.Article;
import com.hins.myreader.util.SharedPreferUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hins on 2017/8/27.
 */

public class FavouriteArticleListFragment extends BaseFragment {

    private static final String TAG = "FavouriteArticleListFra";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FavouriteArticleListAdapter mAdapter;

    private List<Article> mArticles;

    public static FavouriteArticleListFragment newInstance() {
        return new FavouriteArticleListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticles = new ArrayList<>();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_article, container, false);

        initView(view);

        Log.d(TAG, "onCreateView: ");

        return view;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favourite_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavouriteArticleListAdapter(mArticles);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mArticles.clear();
        Map<String, String> articlesMap = (Map<String, String>)
                SharedPreferUtil.getAllArticles(getActivity());
        Gson gson = new Gson();

        for (String date : articlesMap.keySet()) {
            String content = articlesMap.get(date);
            Article article = gson.fromJson(content, Article.class);
            mArticles.add(article);
        }
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "initData: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}
