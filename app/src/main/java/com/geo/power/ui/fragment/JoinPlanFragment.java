package com.geo.power.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geo.power.ui.activity.MyPlanActivity;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 * 我参与的计划
 */
public class JoinPlanFragment extends BaseFragment {
    private static JoinPlanFragment mInstance;

    public static JoinPlanFragment getInstance() {
        if (mInstance == null) {
            mInstance = new JoinPlanFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_joinplan, null);
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
