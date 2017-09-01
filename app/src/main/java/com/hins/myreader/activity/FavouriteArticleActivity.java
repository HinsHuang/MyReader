package com.hins.myreader.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hins.myreader.R;
import com.hins.myreader.fragment.EverydayArticleFragment;

public class FavouriteArticleActivity extends AppCompatActivity {

    private static final String TAG = "FavouriteArticleActivit";

    private static final String ARG_DATE = "DATE";

    private String mDate;

    private Toolbar mToolbar;


    public static void start(Context context, String date) {
        Intent i = new Intent(context, FavouriteArticleActivity.class);
        i.putExtra(ARG_DATE, date);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_article);

        mDate = getIntent().getStringExtra(ARG_DATE);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.collected_article);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment_container, EverydayArticleFragment.newInstance(mDate))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }
}
