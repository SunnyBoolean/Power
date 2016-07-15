package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geo.com.geo.power.Constants;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.github.lazylibrary.util.DateUtil;
import com.github.lazylibrary.util.SerializeUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/20.
 * 首页动态
 */
public class HomeDongtaiFragment extends BaseFragment {
    private static HomeDongtaiFragment mInstance;
    private SwipeRefreshLayout mSwipeRefreshView;
    private ListView mDataListView;
    private View mEmptyVie;
    private final int mPageSize = 10;
    private int mCurPage = 0;
    private List<PlanInfo> mPlanInfoDatas = new ArrayList<PlanInfo>();
    private boolean hasMore = false;
    private PlanAdapter mPlanAdapter;
    private View mRootView;
    public static HomeDongtaiFragment getInstance() {
        if (mInstance == null) {
            mInstance = new HomeDongtaiFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.home_dongtai_layout, null);
        mEmptyVie = mRootView.findViewById(R.id.home_dongtai_empty);
        mDataListView = (ListView) mRootView.findViewById(R.id.mhome_dongtai_rfshlistview);
        mSwipeRefreshView = (SwipeRefreshLayout) mRootView.findViewById(R.id.home_dongtai_swipeLayout);
        initData();
        initSwipeRefresh();
        return mRootView;
    }

    private void initData() {
        mDataListView.setEmptyView(mEmptyVie);
        mPlanAdapter = new PlanAdapter(mPlanInfoDatas);
        loadPlaninfoData(true);
    }


    private void initSwipeRefresh() {
        mSwipeRefreshView.setColorSchemeResources(R.color.material_red_A700,
                R.color.material_deepOrange_A700,
                R.color.material_indigo_900,
                R.color.material_teal_900);
        mSwipeRefreshView.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshView.setRefreshing(true);
                mSwipeRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshView.setRefreshing(false);
                    }
                },3000);
//                loadData(true);
            }
        });


        //必须处理ListView和SwSwipeRefreshView的冲突，不然每次下拉时都会刷新而不是滑到ListView的顶部才刷新
        mDataListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (mDataListView != null && mDataListView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = mDataListView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = mDataListView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeRefreshView.setEnabled(enable);
            }
        });
    }
    private void loadPlaninfoData(final boolean isrefresh){
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
            query.order("-createdAt");

//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(mPageSize);
//        query.order("-createdAt");//降序排列
        //分页查询，这个是忽略前mPageSize*curpage条数据
        query.setSkip(mPageSize * mCurPage);
        //还需要将计划相关联的用户信息查询出来哟
        UserInfo user = BmobUser.getCurrentUser(mContext, UserInfo.class);
        query.addWhereRelatedTo("mAttention", new BmobPointer(user));
        //必须要加上这一句表明在查询时也将用户信息给查询出来
        query.include("author");
//执行查询方法
        query.findObjects(mContext, new FindListener<PlanInfo>() {
            @Override
            public void onSuccess(List<PlanInfo> object) {
                //如果返回的数据小于每页数据说明没有更多数据了
                mSwipeRefreshView.setRefreshing(false);
//                mLoadMorte.setVisibility(View.GONE);
//                mFooterView.setVisibility(View.GONE);
                if (!isrefresh) {  //如果是刷新就不用管底部了
                } else {
                    //如果是刷新还需要先把之前的数据清除掉
                    mPlanInfoDatas.clear();
                }
                if (object.size() == mPageSize) {
                    hasMore = true;
                } else {
                    hasMore = false;
//                    mDataListView.removeFooterView(mFooterView);
                }

                SerializeUtils.serialization(mContext, Constants.CACHE_HOME_DATA_FILENAME, object);
                mPlanInfoDatas.addAll(object);
                mPlanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                mSwipeRefreshView.setRefreshing(false);
                showSnackBar(mRootView, "网络连接失败，请检查网络是开启" + msg);
            }
        });
    }
    /**
     * 创建适配器
     */
    private class PlanAdapter extends BaseAdapter {
        List<PlanInfo> datas;
        final String TAG = "MyAdapter";

        public PlanAdapter(List<PlanInfo> datas) {
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
            HViewHolder holder;
            if (convertView == null) {
                holder = new HViewHolder();
                convertView = View.inflate(mContext, R.layout.list_home_plan_item, null);
                holder.contentImg = (ImageView) convertView.findViewById(R.id.list_homeplan_img_gridview);
                holder.mUserIm = (ImageView) convertView.findViewById(R.id.list_homeplan_userimg);
                holder.mUsernameTv = (TextView) convertView.findViewById(R.id.list_homeplan_username);
                holder.mCreateTime = (TextView) convertView.findViewById(R.id.list_homeplan_plantime);
                holder.mContent = (TextView) convertView.findViewById(R.id.home_plan_jcioancds);
                holder.mZan = (TextView) convertView.findViewById(R.id.list_homeplan_zan);
                holder.mComment = (TextView) convertView.findViewById(R.id.list_homeplan_comment);
                holder.mCanyu = (TextView) convertView.findViewById(R.id.list_homeplan_scan);
                holder.more = convertView.findViewById(R.id.home_listitem_moreu);
                holder.myzxtv = (TextView) convertView.findViewById(R.id.home_haddotimes);
                convertView.setTag(holder);
            } else {
                holder = (HViewHolder) convertView.getTag();
            }
            final PlanInfo info = datas.get(position);
            //设置图片
//            holder.imgGirdView.setAdapter(new GridAdapter(info.picLists));
            //设置用户头像
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(info.author.uimg, holder.mUserIm, defaultOptions);
            if (info.picLists != null && info.picLists.size() > 0)
                ImageLoader.getInstance().displayImage(info.picLists.get(0), holder.contentImg, defaultOptions);
            //设置用户昵称
            holder.mUsernameTv.setText(info.author.getUsername());
            //设置创建时间
            String time = info.getCreatedAt();
            Calendar cal = DateUtil.str2Calendar(time, "yyyy-MM-dd HH:mm:ss");
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            holder.mCreateTime.setText(year + "-" + month + "-" + day);
            //设置文本内容
            holder.mContent.setText(info.content);
            //设置点赞数
            holder.mZan.setText(info.zanToatl + " ");
            //设置鼓励/评论
            holder.mComment.setText(info.commentToal + " ");
            //设置参与总数
            holder.mCanyu.setText(info.dovisition + " ");
            //执行次数
            holder.myzxtv.setText(info.hadDotimes + " ");
            UserInfo user = UserInfo.getCurrentUser(mContext, UserInfo.class);
            if (info.uid.equals(user.getObjectId())) {
                holder.more.setVisibility(View.GONE);
            }
            //点击更多
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            final TextView temp = holder.mZan;
            //点赞单击+1
//            holder.mZan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    info.increment("zanToatl"); // 分数递增1
//                    info.update(mContext);
//                    //已有赞数+
//                    int tota = info.zanToatl + 1;
//                    temp.setText(tota + "");
//                }
//            });
            return convertView;
        }

        private class HViewHolder {
            ImageView contentImg;
            //内容图片
            GridView imgGirdView;
            //用户头像
            ImageView mUserIm;
            //用户名称
            TextView mUsernameTv;
            //发表时间
            TextView mCreateTime;
            //文本内容
            TextView mContent;
            //点赞数
            TextView mZan;
            //鼓励评论
            TextView mComment;
            //参与数目
            TextView mCanyu;
            //已执行数
            TextView myzxtv;
            //点击查看更多
            View more;

        }
    }

}
