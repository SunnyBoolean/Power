package com.geo.power.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geo.power.ui.activity.DreamListActivity;
import com.geo.power.ui.activity.MessageNotificationActivity;
import com.geo.power.ui.activity.NoteListActivity;
import com.geo.power.ui.activity.SuggestionActivity;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 */
public class PersonalCenterFragment extends BaseFragment {
    private static PersonalCenterFragment mInstance;
    private View mNoteBtn, mYjfkBtn, mMessageBtn, mDreamPingziBtn;

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
        initListener();

        return content;
    }

    private void initListener() {
        mNoteBtn.setOnClickListener(this);
        mYjfkBtn.setOnClickListener(this);
        mMessageBtn.setOnClickListener(this);
        mDreamPingziBtn.setOnClickListener(this);
    }

    @Override
    protected void handlerClick(View view) {
        super.handlerClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.personal_my_note:   //随想笔记
                intent.setClass(mContext, NoteListActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_yjfk:  //意见反馈
                intent.setClass(mContext, SuggestionActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_xiaoxibutton:  //消息
                intent.setClass(mContext, MessageNotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_dream_pingzi:
                intent.setClass(mContext, DreamListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
