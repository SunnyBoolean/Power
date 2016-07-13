package com.geo.power.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.PlanHistoryInfo;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.power.ui.activity.ImageShowActivity;
import com.geo.power.ui.activity.MyPlanDetailActivity;
import com.github.lazylibrary.util.DateUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/20.
 * 我的计划我的历史记录
 */
public class MyPlanRecordFragment extends BaseFragment {
    private static MyPlanRecordFragment mInstance;
    private ListView mHistoryListView;
    private List<PlanInfo> mData;
    private HistoryAdapter mAdapter;
    private String mPlanId;
    private final int mPageSize = 10;
    private int mCurPage = 0;
    private boolean hasMore = false;
    private View mFooterView,mEmptyView;

    public static MyPlanRecordFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MyPlanRecordFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.doplan_history_layout, null);
        mHistoryListView = (ListView) content.findViewById(R.id.history_doplan_list);
        mFooterView = View.inflate(mContext, R.layout.myplan_list_footer, null);
        mEmptyView = View.inflate(mContext,R.layout.history_empty,null);
        mFooterView.setVisibility(View.GONE);
        initData();
        initListener();
        return content;
    }

    private void initData() {
        Bundle bundle = getArguments();
        mPlanId = bundle.getString("planinfo");
        if (mData == null) {
            mData = new ArrayList<PlanInfo>();
        }
        mAdapter = new HistoryAdapter(mData);
        mHistoryListView.setAdapter(mAdapter);
        loadHistory();
    }

    private void loadHistory() {
        mHistoryListView.addFooterView(mFooterView);
        mHistoryListView.setEmptyView(mEmptyView);
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
//查询playerName叫“比目”的数据
        BmobQuery<PlanInfo> eq3 = new BmobQuery<PlanInfo>();
        eq3.addWhereEndsWith("originalPlanId", mPlanId);
        BmobQuery<PlanInfo> eq4 = new BmobQuery<PlanInfo>();
        eq4.addWhereEndsWith("uid", BmobUser.getCurrentUser(mContext, UserInfo.class).getObjectId());
        List<BmobQuery<PlanInfo>> queries = new ArrayList<BmobQuery<PlanInfo>>();
        queries.add(eq3);
        queries.add(eq4);

        query.and(queries);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(mPageSize);
//执行查询方法
        query.findObjects(mContext, new FindListener<PlanInfo>() {
            @Override
            public void onSuccess(List<PlanInfo> object) {
                // TODO Auto-generated method stub
                if(object==null){
                    return;
                }
                //如果还有更多数据
                if (object.size() == mPageSize) {
                    hasMore = true;
                } else {
                    hasMore = false;
                    mHistoryListView.removeFooterView(mFooterView);
                }
                mData.addAll(object);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    private void initListener() {
        //对ListView进行监听，如果滚动到底部就自行加载数据
        mHistoryListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastItem = mHistoryListView.getLastVisiblePosition();
                if (lastItem + 1 == totalItemCount) {  //说明已经滑到屏幕底部了
                    //如果有更多就加载
                    if (hasMore) {
                        mCurPage++;
//                        mLoadMorte.setVisibility(View.VISIBLE);
                        mFooterView.setVisibility(View.VISIBLE);
                    } else {

                    }
                }
//解决ListView和SwipeRefreshLayout的下拉冲突问题
                boolean enable = false;
                if (mHistoryListView != null && mHistoryListView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = mHistoryListView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = mHistoryListView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
            }

            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
            }
        });
        mHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, MyPlanDetailActivity.class);
                PlanHistoryInfo info = (PlanHistoryInfo) mAdapter.getItem(position);
                intent.putExtra("plan_info", info);
//                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    private class HistoryAdapter extends BaseAdapter {
        private List<PlanInfo> data;
        private String lastStr = "";

        public HistoryAdapter(List<PlanInfo> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PViewHolder holder = null;
            PlanInfo info = data.get(position);
            if (convertView == null) {
                holder = new PViewHolder();
                convertView = View.inflate(mContext, R.layout.item_plan_history_list_content_te, null);
                holder.contentTv = (TextView) convertView.findViewById(R.id.plan_history_itrem_content);
                holder.timeTv = (TextView) convertView.findViewById(R.id.plan_history_item_ctime);
                holder.imgGridView = (GridView) convertView.findViewById(R.id.plan_history_item_img_gridview_myplanre);
                convertView.setTag(holder);
            } else {
                holder = (PViewHolder) convertView.getTag();
            }
            holder.imgGridView.setAdapter(new GridAdapter(info.picLists));
            holder.imgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, ImageShowActivity.class);
                    startActivity(intent);
                }
            });
            String time = info.getCreatedAt();
            Calendar cal = DateUtil.str2Calendar(time, "yyyy-MM-dd HH:mm:ss");
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            holder.timeTv.setText(month + "/" + day);
            holder.contentTv.setText(info.content);
            return convertView;
        }

        private class PViewHolder {
            TextView contentTv;
            TextView timeTv;
            GridView imgGridView;
        }
    }

    /**
     * 图片适配器
     */
    private class GridAdapter extends BaseAdapter {
        private List<String> mPics;

        public GridAdapter(List<String> pics) {
            this.mPics = pics;
        }

        @Override
        public int getCount() {
            return mPics.size();
        }

        @Override
        public Object getItem(int position) {
            return mPics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_gridview, null);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.item_gridview_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mPics.get(position), holder.mImageView, defaultOptions);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mData.clear();

    }
}
