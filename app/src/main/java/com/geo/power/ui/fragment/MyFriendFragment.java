package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/20.
 * 好友列表
 */
public class MyFriendFragment extends BaseFragment{
    private static MyFriendFragment mInstance;
    public static MyFriendFragment getmInstance(){
        if(mInstance == null){
            mInstance = new MyFriendFragment();
        }
        return mInstance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
