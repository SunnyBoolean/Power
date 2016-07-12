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
import android.widget.TextView;

import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.power.ui.activity.MyPlanActivity;
import com.geo.power.ui.activity.MyPlanDetailActivity;
import com.github.lazylibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
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
    private View mEmptyVie;

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
        mEmptyVie = content.findViewById(R.id.empty_doneplansadx);
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
                PlanInfo info = (PlanInfo) mAdapter.getItem(position);
                intent.putExtra("isDone", 0);
                intent.putExtra("plan_info",info);
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
        View empty = View.inflate(mContext,R.layout.empty_donw_plan,null);
        mDoneListView.setEmptyView(mEmptyVie);
        loadPlan();
    }

    private void loadPlan() {
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
//查询我的
        query.addWhereEqualTo("uid", BmobUser.getCurrentUser(mContext, UserInfo.class).getObjectId());
        BmobQuery<PlanInfo> eq3 = new BmobQuery<PlanInfo>();
        eq3.addWhereEqualTo("originalPlanId", "empty");
        BmobQuery<PlanInfo> eq4 = new BmobQuery<PlanInfo>();
        eq4.addWhereEqualTo("isDone", 1);
        List<BmobQuery<PlanInfo>> queries = new ArrayList<BmobQuery<PlanInfo>>();
        queries.add(eq3);
        queries.add(eq4);
        query.and(queries);
        query.order("-createdAt");//降序排列
        query.findObjects(mContext, new FindListener<PlanInfo>() {
            @Override
            public void onSuccess(List<PlanInfo> object) {
                mDatas.clear();
                mDatas.addAll(object);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.showToast(mContext, "查询失败：" + msg);
            }
        });
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
            PViewHolder holder;
            if(convertView == null){
                holder = new PViewHolder();
                convertView = View.inflate(mContext, R.layout.item_done_plan_list, null);
           holder.time = (TextView) convertView.findViewById(R.id.done_plamn_ytoime);
                holder.content = (TextView) convertView.findViewById(R.id.done_plan_contema);
                holder.spandtim= (TextView) convertView.findViewById(R.id.item_doneplkan_spand);
                convertView.setTag(holder);
            }else{
                holder = (PViewHolder) convertView.getTag();
            }
            PlanInfo info = mPlanDatas.get(position);
            holder.time.setText(info.getCreatedAt());
            holder.content.setText(info.content);
            holder.spandtim.setText("用时165天");
            return convertView;
        }

        class PViewHolder {
            //创建时间
            TextView time;
            //计划内容
            TextView content;
            //计划完成所花的时间
            TextView spandtim;
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
