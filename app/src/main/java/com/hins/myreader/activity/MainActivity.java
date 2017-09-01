package com.hins.myreader.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hins.myreader.R;
import com.hins.myreader.adapter.MyFragmentPagerAdapter;
import com.hins.myreader.fragment.EverydayArticleFragment;
import com.hins.myreader.fragment.FavouriteArticleListFragment;
import com.hins.myreader.fragment.ZhihuDailyStoryFragment;
import com.hins.myreader.util.DateUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private List<Fragment> mFragments;

    private long mExistTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExistTime = 0;

        mFragments = new ArrayList<>();

        mFragments.add(ZhihuDailyStoryFragment.newInstance(DateUtil.getTomorrow()));
        mFragments.add(EverydayArticleFragment.newInstance(DateUtil.getToday()));
        mFragments.add(EverydayArticleFragment.newInstance(null));
        mFragments.add(FavouriteArticleListFragment.newInstance());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "此功能正在开发...", Toast.LENGTH_SHORT).show();
            return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private void doExitApp() {
        if ((System.currentTimeMillis() - mExistTime) > 2000) {
            mExistTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.exist_again, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    /*
           设置menu的文字和图片同时显示
         */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

}
