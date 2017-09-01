package com.hins.myreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.hins.myreader.R;
import com.hins.myreader.modle.Article;
import com.hins.myreader.util.HtmlParse;
import com.hins.myreader.util.RetrofitUtil;
import com.hins.myreader.util.SharedPreferUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hins on 2017/8/27.
 */

public class EverydayArticleFragment extends Fragment {

    private static final String TAG = "EverydayArticleFragment";

    private static final String ARG_DATE = "DATE";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTitle;
    private ToggleButton mLike;
    private TextView mPublishDate;
    private TextView mAuthor;
    private TextView mContent;

    private Article mArticle;
    private String mDate;
    private String mCurrentDate;


    public static EverydayArticleFragment newInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DATE, date);

        EverydayArticleFragment fragment = new EverydayArticleFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDate = getArguments().getString(ARG_DATE);

        mArticle = new Article();

        Log.d(TAG, "onCreate: " + mDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_everyday_article, container, false);

        initView(view);
        initData();

        Log.d(TAG, "onCreateView: " + mDate);

        return view;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mTitle = (TextView) view.findViewById(R.id.everyday_article_title);

        mLike = (ToggleButton) view.findViewById(R.id.like);
        mLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mArticle.data != null) {
                    if (isChecked) {
                        Gson gson = new Gson();
                        String content = gson.toJson(mArticle);
                        SharedPreferUtil.saveArticle(getActivity(), mArticle.data.dateChain.curr, content);
                    } else {
                        SharedPreferUtil.removeArticle(getActivity(), mArticle.data.dateChain.curr);
                    }
                }
            }
        });

        mPublishDate = (TextView) view.findViewById(R.id.everyday_article_publish_date);
        mAuthor = (TextView) view.findViewById(R.id.everyday_article_author);
        mContent = (TextView) view.findViewById(R.id.everyday_article_content);

    }

    private void initData() {
        Log.d(TAG, "initData: ");
        Call<Article> call = RetrofitUtil.initDataForEveryDayArticle(mDate);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Article article = response.body();
                //保存当前文章的日期
                mCurrentDate = article.data.dateChain.curr;

                mArticle = article;
                updateUI(mArticle);
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mLike.setChecked(SharedPreferUtil.containsArticle(getActivity(), mCurrentDate));
        Log.d(TAG, "onResume: " + mDate);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + mDate);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + mDate);
    }

    private void updateUI(Article article) {
        mTitle.setText(HtmlParse.parseHtml(article.data.title));
        mPublishDate.setText(article.data.dateChain.curr);
        mAuthor.setText(HtmlParse.parseHtml(article.data.author));
        mContent.setText(HtmlParse.parseHtml(indent(article.data.content)));
        // 判断是否在收藏中，并更新like按钮状态
        mLike.setChecked(SharedPreferUtil.containsArticle(getActivity(), mCurrentDate));
    }

    //对首行进行空两格处理
    private String indent(String content) {
        Pattern p = Pattern.compile("<p>");
        Matcher m = p.matcher(content);
        //把首行的<p>加上两个空格
        content = m.replaceAll("<p>\u3000\u3000");
        return content;
    }


}
