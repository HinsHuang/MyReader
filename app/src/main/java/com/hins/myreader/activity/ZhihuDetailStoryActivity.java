package com.hins.myreader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hins.myreader.R;
import com.hins.myreader.modle.ZhihuDetailStory;
import com.hins.myreader.util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hins on 2017/8/29.
 */

public class ZhihuDetailStoryActivity extends AppCompatActivity {

    private static final String TAG = "ZhihuDetailStoryActivit";

    public static final String STORY_ID = "com.hins.myreader.story_id";

    private int mId;

    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mStoryImage;
    private WebView mWebView;

    private ZhihuDetailStory mDetailStory;

    public static void start(Context context, int id) {
        Intent i = new Intent(context, ZhihuDetailStoryActivity.class);
        i.putExtra(STORY_ID, id);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_detail_story);
        mId = getIntent().getIntExtra(STORY_ID, 0);

        initView();

        loadZhihuDetailStory(mId);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadZhihuDetailStory(int id) {
        Call<ZhihuDetailStory> call = RetrofitUtil.initDataForZhihuDetailStory(id);
        call.enqueue(new Callback<ZhihuDetailStory>() {
            @Override
            public void onResponse(Call<ZhihuDetailStory> call, Response<ZhihuDetailStory> response) {
                mDetailStory = response.body();
                updateUI(mDetailStory);
//                Log.d(TAG, mDetailStory.title);
//                Log.d(TAG, mDetailStory.image);
//                Log.d(TAG, mDetailStory.imageSource);
//                Log.d(TAG, mDetailStory.prefix);
//                Log.d(TAG, mDetailStory.shareUrl);
//                Log.d(TAG, mDetailStory.css.get(0));
//                Log.d(TAG, mDetailStory.images.get(0));
//                Log.d(TAG, "" + mDetailStory.id);
//                Log.d(TAG, mDetailStory.body);
            }

            @Override
            public void onFailure(Call<ZhihuDetailStory> call, Throwable t) {

            }
        });
    }

    private void initView() {
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mStoryImage = (ImageView) findViewById(R.id.zhihu_story_image);
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);


    }

    private void updateUI(ZhihuDetailStory detailStory) {
        mCollapsingToolbar.setTitle(detailStory.title);
        Glide.with(this).load(detailStory.image).into(mStoryImage);

        String html = converZhihuContent(detailStory.body);
        mWebView.loadDataWithBaseURL("file:///android_asset", html, "text/html", "UTF-8", null);

    }

    private String converZhihuContent(String content) {
        content = content.replaceAll("<div class=\"headline\">", "");
        content = content.replaceAll("<div class=\"img-place-holder\">", "");

        // 加载本地assets文件夹中的css,可在知乎日报中获取
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu.css\" type=\"text/css\">";

        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>")
                .append("<html lang=\"zh\">")
                .append("<head>")
                .append("<meta charset=\"utf-8\"/>")
                .append(css)
                .append("</head>")
                .append("<body>")
                .append(content)
                .append("</body>")
                .append("</html>");

        return builder.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                Toast.makeText(this, "此功能正在开发...", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
