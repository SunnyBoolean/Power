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
 */
public class PersonalCenterFragment extends BaseFragment {
    private static PersonalCenterFragment mInstance;
    private View mMyPlan;

    public static PersonalCenterFragment getInstance() {
        if (mInstance == null) {
            mInstance = new PersonalCenterFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.profile_setting, null);
        mMyPlan = content.findViewById(R.id.personal_my_aim);
        initListener();

        return content;
    }

    private void initListener() {
        mMyPlan.setOnClickListener(this);
    }

    @Override
    protected void handlerClick(View view) {
        super.handlerClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.personal_my_aim:  //我的计划
                intent.setClass(mContext, MyPlanActivity.class);
                startActivity(intent);
                break;

        }
    }
}
