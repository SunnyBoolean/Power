package com.geo.power.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geo.com.geo.power.util.DensityUtil;
import com.geo.power.ui.activity.DreamListActivity;
import com.geo.power.ui.activity.MessageNotificationActivity;
import com.geo.power.ui.activity.MyFavoriteActivity;
import com.geo.power.ui.activity.SuggestionActivity;
import com.geo.widget.BadgeView;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 */
public class PersonalCenterFragment extends BaseFragment {
    private static PersonalCenterFragment mInstance;
    private View mNoteBtn, mYjfkBtn, mMessageBtn, mDreamPingziBtn,mMyFavorite;

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
        mNoteBtn = content.findViewById(R.id.personal_my_note);
        mYjfkBtn = content.findViewById(R.id.personal_yjfk);
        mMessageBtn = content.findViewById(R.id.personal_xiaoxibutton);
        mDreamPingziBtn = content.findViewById(R.id.personal_dream_pingzi);
        mMyFavorite = content.findViewById(R.id.personal_floow_aim);

        initListener();
        BadgeView badge = new BadgeView(getActivity());
        badge.setTargetView(mMessageBtn);

        badge.setBadgeCount(42);
        badge.setBackground(6, Color.RED);
        int h = DensityUtil.dip2px(mContext, 25);
        badge.setHeight(h);
        badge.setWidth(h);
        return content;
    }

    private void initListener() {
        mNoteBtn.setOnClickListener(this);
        mYjfkBtn.setOnClickListener(this);
        mMessageBtn.setOnClickListener(this);
        mDreamPingziBtn.setOnClickListener(this);
        mMyFavorite.setOnClickListener(this);
    }

    @Override
    protected void handlerClick(View view) {
        super.handlerClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.personal_yjfk:  //意见反馈
                intent.setClass(mContext, SuggestionActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_xiaoxibutton:  //消息
                intent.setClass(mContext, MessageNotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_dream_pingzi:   //漂流瓶子
                intent.setClass(mContext, DreamListActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_floow_aim:  //我的收藏
                intent.setClass(mContext, MyFavoriteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
