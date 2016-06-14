package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.geo.com.geo.power.bean.DreamInfo;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/13.
 * 未实现的梦想
 */
public class NotDoneDreamFragmetn extends BaseFragment {
    private static NotDoneDreamFragmetn mInstance;
    private ListView mNotDoneDreamListView;
    private List<DreamInfo> mDreamDatas;
    private NotDreamAdapter mAdapter;

    public static NotDoneDreamFragmetn getInstance() {
        if (mInstance == null) {
            mInstance = new NotDoneDreamFragmetn();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.notdone_dream_layout, null);
        mNotDoneDreamListView = (ListView) content.findViewById(R.id.add_notdone_dream);
        initData();
        return content;
    }

    private void initData() {
        if (mDreamDatas == null) {
            mDreamDatas = new ArrayList<DreamInfo>();
        } else {
            mDreamDatas.clear();
        }
        mAdapter = new NotDreamAdapter(mDreamDatas);
        mNotDoneDreamListView.setAdapter(mAdapter);


        for(int i=0;i<=5;i++){
            DreamInfo info = new DreamInfo();
            mDreamDatas.add(info);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 未实现的心愿适配器
     * 到时候需要根据时间查询来分组，这个适配器就是放置相同时间的分组的
     */
    private class NotDreamAdapter extends BaseAdapter {
        private List<DreamInfo> datas;
        public NotDreamAdapter(List<DreamInfo> datas){
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
            convertView = View.inflate(mContext,R.layout.item_notdonedream_layout,null);
            GridView grid = (GridView) convertView.findViewById(R.id.noddone_dream_item_img_gridview);
            grid.setAdapter(new NotRDreamAdapter(mDreamDatas));
            return convertView;
        }
    }
    private class NotRDreamAdapter extends BaseAdapter {
        private List<DreamInfo> datas;
        public NotRDreamAdapter(List<DreamInfo> datas){
            this.datas = datas;
        }
        @Override
        public int getCount() {
            return 1;
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
            convertView = View.inflate(mContext,R.layout.not_donedream_item,null);
            return convertView;
        }
    }
}
