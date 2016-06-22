package com.geo.power.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.power.ui.activity.MyPlanActivity;
import com.geo.power.ui.activity.MyPlanDetailActivity;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 * 已完成的计划
 */
public class DonePlanFragment extends BaseFragment {
    private static DonePlanFragment mInstance;
    private ListView mDoneListView;
    private List<PlanInfo> mDatas;
    private PAdapter mAdapter;

    public static DonePlanFragment getInstance() {
        if (mInstance == null) {
            mInstance = new DonePlanFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_done_plan, null);
        mDoneListView = (ListView) content.findViewById(R.id.plan_donefragment_lv);
        initListener();
        initData();
        return content;
    }

    private void initListener() {
        mDoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, MyPlanDetailActivity.class);
                //计划是否完成，0表示未完成，1表示完成
                intent.putExtra("isDone",0);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        if (mDatas == null) {
            mDatas = new ArrayList<PlanInfo>();
        } else {
            mDatas.clear();
        }
        mAdapter = new PAdapter(mDatas);
        mDoneListView.setAdapter(mAdapter);

        for (int i = 0; i < 10; i++) {
            mDatas.add(new PlanInfo());
        }
        mAdapter.notifyDataSetChanged();
    }

    private class PAdapter extends BaseAdapter {
        List<PlanInfo> mPlanDatas;

        public PAdapter(List<PlanInfo> data) {
            this.mPlanDatas = data;
        }

        @Override
        public int getCount() {
            return mPlanDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mPlanDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext,R.layout.item_done_plan_list,null);
            return convertView;
        }
    }

    @Override
    protected void handlerClick(View view) {
        super.handlerClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {

        }
    }
}
