package com.hins.myreader.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by Hins on 2017/9/1.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    public Context mContext;
    protected View mRootView;
    protected boolean isVisible;
    private boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initData();
        Log.d(TAG, "lazyLoad: ");
    }

    protected void onInvisible() {

        Log.d(TAG, "onInvisible: ");

    }

    public abstract void initData();

    public abstract void initView(View view);

}
