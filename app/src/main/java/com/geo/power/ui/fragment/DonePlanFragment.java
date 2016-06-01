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
 * 已完成的计划
 */
public class DonePlanFragment extends BaseFragment {
    private static DonePlanFragment mInstance;

    public static DonePlanFragment getInstance() {
        if (mInstance == null) {
            mInstance = new DonePlanFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_done_plan, null);
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
