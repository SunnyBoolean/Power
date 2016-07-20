package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/20.
 * 会话Fragment
 */
public class MesgConversionFragment extends BaseFragment{
    private static MesgConversionFragment mInstance;
    public static MesgConversionFragment getmInstance(){
        if(mInstance == null){
            mInstance = new MesgConversionFragment();
        }
        return mInstance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
