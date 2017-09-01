package com.hins.myreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hins.myreader.R;
import com.hins.myreader.adapter.ZhihuStoryTitleAdapter;
import com.hins.myreader.modle.ZhihuDailyStory;
import com.hins.myreader.modle.ZhihuStoryTitle;
import com.hins.myreader.util.DateUtil;
import com.hins.myreader.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hins on 2017/8/27.
 */

public class ZhihuDailyStoryFragment extends Fragment {

    private static final String TAG = "ZhihuDailyArticleFragme";

    private static final String ARG_DATE = "DATE";

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private TextView mTitle;
    private ImageView mTitleImage;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;

    private List<ZhihuStoryTitle> mTitles;
    private ZhihuStoryTitleAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private ZhihuDailyStory mStory;

    private String mDate;

    public static ZhihuDailyStoryFragment newInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DATE, date);

        ZhihuDailyStoryFragment fragment = new ZhihuDailyStoryFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = getArguments().getString(ARG_DATE);

        mStory = new ZhihuDailyStory();

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        mTitles = new ArrayList<>();

        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_daily_story, container, false);
        initView(view);

        load(mDate);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRandom();
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        mCalendar.set(mYear, mMonth, mDay--);
                        Log.d("Testing", DateUtil.mFormat.format(mCalendar.getTime()));
                        loadMore(DateUtil.mFormat.format(mCalendar.getTime()));
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });

        Log.d(TAG, "onCreateView: ");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        Log.d(TAG, "" + mTitles.size());
    }

    private void initView(View view) {
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.zhihu_swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTitle = (TextView) view.findViewById(R.id.everyday_article_title);
        mTitleImage = (ImageView) view.findViewById(R.id.zhihu_story_image);
    }

    private void initData(String date, final boolean isClear) {
        mSwipeRefresh.setRefreshing(true);

        Call<ZhihuDailyStory> call = RetrofitUtil.initDataForZhihuDailyStory(date);
        call.enqueue(new Callback<ZhihuDailyStory>() {
            @Override
            public void onResponse(Call<ZhihuDailyStory> call, Response<ZhihuDailyStory> response) {
                mStory = response.body();

                if (isClear) {
                    mTitles.clear();
                }

                loadStory(mStory, isClear);
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ZhihuDailyStory> call, Throwable t) {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void loadStory(ZhihuDailyStory story, boolean isClear) {

        for (int i = 0; i < story.stories.size(); i++) {
            ZhihuStoryTitle title = new ZhihuStoryTitle();
            title.setTitle(story.stories.get(i).title);
            title.setUrl(story.stories.get(i).images.get(0));
            title.setId(story.stories.get(i).id);

            mTitles.add(title);
        }

        if (isClear) {
            mAdapter = new ZhihuStoryTitleAdapter(mTitles);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    private void load(String date) {
        initData(date, true);
    }

    private void loadMore(String date) {
        initData(date, false);

    }

    private void refreshRandom() {
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        initData(DateUtil.getTomorrow(), true);
    }

}
