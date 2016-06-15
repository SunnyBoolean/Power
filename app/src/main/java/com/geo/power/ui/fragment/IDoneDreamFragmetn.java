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
 * 我帮别人实现的梦想
 */
public class IDoneDreamFragmetn extends BaseFragment {
    private static IDoneDreamFragmetn mInstance;
    private ListView mJoinListView;
    private List<DreamInfo> mDatas;
    private DDAdapter mAdapter;
    public static IDoneDreamFragmetn getInstance() {
        if (mInstance == null) {
            mInstance = new IDoneDreamFragmetn();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.dream_i_joined_layout, null);
        mJoinListView = (ListView) content.findViewById(R.id.dream_i_joined_listview);
        initData();
        return content;
    }

    private void initData() {
        if (mDatas == null) {
            mDatas = new ArrayList<DreamInfo>();
        } else {
            mDatas.clear();
        }
        mAdapter = new DDAdapter(mDatas);
        mJoinListView.setAdapter(mAdapter);
        for(int i=0;i<30;i++){
            DreamInfo info = new DreamInfo();
            mDatas.add(info);
        }
        mAdapter.notifyDataSetChanged();
    }
    private class DDAdapter extends BaseAdapter{
         private List<DreamInfo> datas;
        public DDAdapter(List<DreamInfo> datas){
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
            convertView = View.inflate(mContext,R.layout.dream_ijoin_list_item,null);
            return convertView;
        }
    }
}
