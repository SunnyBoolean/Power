package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/13.
 * 未实现的梦想
 */
public class NotDoneDreamFragmetn extends BaseFragment {
    private static NotDoneDreamFragmetn mInstance;

    public static NotDoneDreamFragmetn getInstance() {
        if (mInstance == null) {
            mInstance = new NotDoneDreamFragmetn();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.add_dream_layout, null);
        return content;
    }
}
