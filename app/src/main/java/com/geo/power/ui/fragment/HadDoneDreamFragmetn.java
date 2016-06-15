package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.geo.com.geo.power.bean.DreamInfo;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/13.
 * 已实现的梦想
 */
public class HadDoneDreamFragmetn extends BaseFragment {
    private static HadDoneDreamFragmetn mInstance;
    private ListView mDoneListView;
    private List<DreamInfo> mDreamDatas;
    private DoneAdapter mAdapter;

    public static HadDoneDreamFragmetn getInstance() {
        if (mInstance == null) {
            mInstance = new HadDoneDreamFragmetn();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.had_unlocked_dream_layout, null);
        mDoneListView = (ListView) content.findViewById(R.id.haddone_dream_listview);
        initData();
        return content;
    }

    private void initData() {
        if (mDreamDatas == null) {
            mDreamDatas = new ArrayList<DreamInfo>();
        } else {
            mDreamDatas.clear();
        }
        mAdapter = new DoneAdapter(mDreamDatas);
        mDoneListView.setAdapter(mAdapter);
        for(int i=0;i<23;i++){
            DreamInfo info = new DreamInfo();
            mDreamDatas.add(info);
        }

    }

    private class DoneAdapter extends BaseAdapter {
        private List<DreamInfo> datas;
        public DoneAdapter(List<DreamInfo> datas){
            this.datas = datas;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext,R.layout.done_dream_list_item,null);
            return convertView;
        }
    }
}
