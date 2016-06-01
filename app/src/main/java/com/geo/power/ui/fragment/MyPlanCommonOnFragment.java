package com.geo.power.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 * 为我加油的Fragment
 */
public class MyPlanCommonOnFragment extends BaseFragment {
    private static MyPlanCommonOnFragment mInstance;

    public static MyPlanCommonOnFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MyPlanCommonOnFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_commonon, null);
        initListener();

        return content;
    }

    private void initListener() {
    }

    @Override
    protected void handlerClick(View view) {
        super.handlerClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {

        }
    }
}
