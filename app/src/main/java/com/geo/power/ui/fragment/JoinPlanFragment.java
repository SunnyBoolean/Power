package com.geo.power.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.power.ui.activity.MyPlanActivity;
import com.geo.power.ui.activity.MyPlanDetailActivity;
import com.github.lazylibrary.util.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 * 我参与的计划
 */
public class JoinPlanFragment extends BaseFragment {
    private static JoinPlanFragment mInstance;
    private ListView mMyPlanListView;
    private List<PlanInfo> mPlanData;
    private PlanAdapter mPlanAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private final int mPageSize = 10;
    private int mCurPage = 0;
    private boolean hasMore = false;
    private View mFooterView;


    public static JoinPlanFragment getInstance() {
        if (mInstance == null) {
            mInstance = new JoinPlanFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_joinplan, null);
        mMyPlanListView = (ListView) content.findViewById(R.id.myplan__join_listview);
        swipeRefreshLayout = (SwipeRefreshLayout) content.findViewById(R.id.home_myplan_myjoin_swipeLayout);
        initCompontent();
        initListener();
        return content;
    }

    private void initListener() {
        //对ListView进行监听，如果滚动到底部就自行加载数据
        mMyPlanListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastItem = mMyPlanListView.getLastVisiblePosition();
                if (lastItem + 1 == totalItemCount) {  //说明已经滑到屏幕底部了
                    //如果有更多就加载
                    if (hasMore) {
                        mCurPage++;
//                        mLoadMorte.setVisibility(View.VISIBLE);
                        mFooterView.setVisibility(View.VISIBLE);
                        loadData(false);
                    } else {

                    }
                }
//解决ListView和SwipeRefreshLayout的下拉冲突问题
                boolean enable = false;
                if (mMyPlanListView != null && mMyPlanListView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = mMyPlanListView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = mMyPlanListView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
                if (mMyPlanListView.getChildCount() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                }
            }

            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
            }
        });
        mMyPlanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, MyPlanDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initCompontent() {
        mPlanData = new ArrayList<PlanInfo>();
        mPlanAdapter = new PlanAdapter(mPlanData);
        mMyPlanListView.setAdapter(mPlanAdapter);
        mMyPlanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, MyPlanDetailActivity.class);
                startActivity(intent);
            }
        });
        mFooterView = View.inflate(mContext, R.layout.myplan_list_footer, null);
        mFooterView.setVisibility(View.GONE);
        initSwipeRefresh();
        loadData(true);
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.material_red_A700,
                R.color.material_deepOrange_A700,
                R.color.material_indigo_900,
                R.color.material_teal_900);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
//        swipeRefreshLayout.setProgressBackgroundColor(R.color.material_white);
//        swipeRefreshLayout.setPadding(20, 20, 20, 20);
//        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
//        swipeRefreshLayout.setDistanceToTriggerSync(50);
//        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里开始刷新任务
                mCurPage = 0;
                loadData(true);
            }
        });
        //一进来就开始刷新
        swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 加载数据
     *
     * @param isrefresh true表示刷新，false表示加载更多
     */
    private void loadData(final boolean isrefresh) {
        mMyPlanListView.addFooterView(mFooterView);
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
//查询我的
//        query.addWhereEqualTo("uid", BmobUser.getCurrentUser(mContext, UserInfo.class).getObjectId());
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(mPageSize);
        query.order("-createdAt");//降序排列
        //分页查询，这个是忽略前mPageSize*curpage条数据
        query.setSkip(mPageSize * mCurPage);
        UserInfo user = BmobUser.getCurrentUser(mContext, UserInfo.class);
        query.addWhereRelatedTo("mLikes", new BmobPointer(user));
        query.include("author");
//执行查询方法
        query.findObjects(mContext, new FindListener<PlanInfo>() {
            @Override
            public void onSuccess(List<PlanInfo> object) {
                //如果返回的数据小于每页数据说明没有更多数据了
                swipeRefreshLayout.setRefreshing(false);
//                mLoadMorte.setVisibility(View.GONE);
                mFooterView.setVisibility(View.GONE);
                if (!isrefresh) {  //如果是刷新就不用管底部了
                } else {
                    //如果是刷新还需要先把之前的数据清除掉
                    mPlanData.clear();
                }
                if (object.size() == mPageSize) {
                    hasMore = true;
                } else {
                    hasMore = false;
                    mMyPlanListView.removeFooterView(mFooterView);
                }
                mPlanData.addAll(object);
                mPlanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                swipeRefreshLayout.setRefreshing(false);
                // TODO Auto-generated method stub
                ToastUtils.showToast(mContext, "查询失败：" + msg);
            }
        });
    }

    @Override
    protected void handlerClick(View view) {
        super.handlerClick(view);
        Intent intent = new Intent();
        switch (view.getId()) {

        }
    }

    private class PlanAdapter extends BaseAdapter {
        List<PlanInfo> mPDatas;
        public PlanAdapter(List<PlanInfo> list){
            this.mPDatas = list;
        }
        @Override
        public int getCount() {
            int size = mPDatas.size();
            return size;
        }

        @Override
        public Object getItem(int position) {
            return mPDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_myplan_join_list, null);
                holder.imageGrid = (GridView) convertView.findViewById(R.id.home_myplan_item_img_gridview);
                holder.mUimg = (ImageView) convertView.findViewById(R.id.home_myplan_item_tag_p_wcy);
                holder.mUname = (TextView) convertView.findViewById(R.id.home_myplan_item_sigin_wcyname);
                holder.mCreateTime = (TextView) convertView.findViewById(R.id.home_mycan_timne);
                holder.mJxcy = (TextView) convertView.findViewById(R.id.home_mycanyu_contim);
                holder.mContent = (TextView) convertView.findViewById(R.id.home_mycanyu_content);
                holder.mYzx = (TextView) convertView.findViewById(R.id.mycanyu_yzx);
                holder.canyz = (TextView) convertView.findViewById(R.id.home_mcy_cyz);
                holder.comment = (TextView) convertView.findViewById(R.id.home_gulis);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PlanInfo info = mPDatas.get(position);
            holder.imageGrid.setAdapter(new GridAdapter(info.picLists));
            //设置用户头像
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(info.author.uimg, holder.mUimg, defaultOptions);
            //用户名
            holder.mUname.setText(info.author.getUsername()+" ");
            //创建时间
            holder.mCreateTime.setText(info.getCreatedAt());
            //内容
            holder.mContent.setText(info.content);
            //已执行总数
            holder.mYzx.setText(info.hadDotimes+" ");
            //评论
            holder.comment.setText(info.commentToal+" ");
            //参与者总数
            holder.canyz.setText(info.dovisition+" ");
            return convertView;
        }

        private class ViewHolder {
            GridView imageGrid;
            //用户头像
            ImageView mUimg;
            //用户名称
            TextView mUname;
            //创建时间
            TextView mCreateTime;
            //点击继续参与
            TextView mJxcy;
            //内容
            TextView mContent;
            //已执行总数
            TextView mYzx;
            //参与者
            TextView canyz;
            //鼓励
            TextView comment;

        }
    }

    /**
     * 图片适配器
     */
    private class GridAdapter extends BaseAdapter {
        private List<String> mPicDatas;
        public GridAdapter(List<String> datas){
            this.mPicDatas = datas;
        }
        @Override
        public int getCount() {
            return mPicDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mPicDatas.get(position);
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
            ImageLoader.getInstance().displayImage(mPicDatas.get(position), holder.mImageView, defaultOptions);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }
}
