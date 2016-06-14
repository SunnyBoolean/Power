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
 * 我的计划参与者
 */
public class MyPlanVisitorFragment extends BaseFragment {
    private static MyPlanVisitorFragment mInstance;

    public static MyPlanVisitorFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MyPlanVisitorFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_myplan_visitor, null);
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
