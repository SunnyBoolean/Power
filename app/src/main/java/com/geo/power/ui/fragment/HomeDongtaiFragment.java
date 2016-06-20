package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/20.
 * 首页动态
 */
public class HomeDongtaiFragment extends BaseFragment{
    private  static HomeDongtaiFragment mInstance;
    public static HomeDongtaiFragment getInstance(){
        if(mInstance == null){
            mInstance = new HomeDongtaiFragment();
        }
        return mInstance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View content = inflater.inflate(R.layout.home_dongtai_layout,null);
    return content;
    }
}
