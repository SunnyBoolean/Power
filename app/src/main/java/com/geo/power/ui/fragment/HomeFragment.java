package com.geo.power.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.geo.com.geo.power.Constants;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.power.ui.activity.DiscoverDetailActivity;
import com.geo.power.ui.activity.ImageShowActivity;
import com.geo.power.ui.activity.MainActivity;
import com.geo.widget.HeaderDecoration;
import com.github.lazylibrary.util.DateUtil;
import com.github.lazylibrary.util.SerializeUtils;
import com.github.lazylibrary.util.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 * 首页
 */
public class HomeFragment extends BaseFragment implements MainActivity.LoadCallback {
    private static HomeFragment mInstance;
    private ListView mDataListView;
    //    private RecyclerView mRecycleView;
    private MyAdapter mAdapter;
    private final String[] mCategorys = {"生活", "健康", "学习", "工作"};
    private ConvenientBanner mConverBanner;
    SwipeRefreshLayout swipeRefreshLayout;
    private final int mPageSize = 10;
    private int mCurPage = 0;
    private View mFooterView;
    /**
     * 默认分类是生活
     */
    private String mCategory = "生活";
    /**
     * 默认排序是时间最新0表示时间最新、1表示参与者最多、2表示执行次数最多、3表示已经完成的计划
     */
    private int mOrder = 0;
    /**
     * 是否有更多数据
     */
    private boolean hasMore = false;
    public static final String[] mPictureUrls = {
            "http://ac-6ptjoad9.clouddn.com/3MekCrFaIezGOmrmbmvkILWjyF2dGIItve4AYXQC",
            "http://ac-6ptjoad9.clouddn.com/aEealv8tKqUxuSn3DHhHKPUQUtkUoVdZcwqN8i9y",
            "http://ac-6ptjoad9.clouddn.com/Itzvdzj3QZG5zR9dNMmPVAZNNviQt5tzJEsaU6jW",
            "http://ac-6ptjoad9.clouddn.com/Q61AFSIOoOipiucsdR8NctVIvkSHimJK9RIZWlnh",
            "http://ac-6ptjoad9.clouddn.com/9JUZAO5WitlZWbKydQSbNxArhR9miKLc5zY9dE6o",
            "http://ac-6ptjoad9.clouddn.com/Akz36NdJjN2oXkXQYe5GqaE9AA2aocsWYQyzTgfq",
            "http://ac-6ptjoad9.clouddn.com/exVLcXBmU4zxOcBtESLtVJ8IMwLXzUZb8rC4aP1H",
            "http://ac-6ptjoad9.clouddn.com/djAnPc8BxGT7dDh3UAK06OOlG0En3OnLFCTC54cv",
            "http://ac-6ptjoad9.clouddn.com/JHMQOVBhqcQ204ERMBarbYbpT1noqSSiASU4MunW",
            "http://ac-6ptjoad9.clouddn.com/MwGpKcdKiEWPG1yl20TcWuVvf9SCe5VQ6gIPwfNi"
    };
    /**
     * 计划数据列表
     */
    private List<PlanInfo> mPlanDataList;

    public static HomeFragment getInstance() {
        if (mInstance == null) {
            mInstance = new HomeFragment();
        }
        return mInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        activity.setmLoadCallBackListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_main_home, null);
        mDataListView = (ListView) content.findViewById(R.id.home_main_recyclerList);
        swipeRefreshLayout = (SwipeRefreshLayout) content.findViewById(R.id.home_swipeLayout);

        initCompontent();

        return content;
    }

    private void initCompontent() {

        if (mPlanDataList == null) {
            mPlanDataList = new ArrayList<PlanInfo>();
        }
        mAdapter = new MyAdapter(mPlanDataList);
        View header = View.inflate(mContext, R.layout.home_recycleview_header, null);
        mConverBanner = (ConvenientBanner) header.findViewById(R.id.home_convenientBanner);

        mDataListView.setAdapter(mAdapter);
        mDataListView.addHeaderView(header);
        mFooterView = View.inflate(mContext, R.layout.myplan_list_footer, null);
        mFooterView.setVisibility(View.GONE);
        mDataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                PlanInfo info = mPlanDataList.get(position - 1);
                Intent intent = new Intent(mContext, DiscoverDetailActivity.class);
                intent.putExtra("plan_info", info);
                startActivity(intent);
            }
        });
        //首先回去从缓存读取首页数据，如果缓存没有找到就去加载
        List<PlanInfo> mInfos = (List<PlanInfo>) SerializeUtils.deserialization(mContext, Constants.CACHE_HOME_DATA_FILENAME);
        if(mInfos!=null){
            mPlanDataList.addAll(mInfos);
        }else{
            loadData(true);
        }
        initBanner();
        initSwipeRefresh();
    }

    /**
     * 加载数据
     *
     * @param isrefresh true表示刷新，false表示加载更多
     */
    private void loadData(final boolean isrefresh) {
        mDataListView.addFooterView(mFooterView);
        if (isrefresh) {
            swipeRefreshLayout.setRefreshing(true);
        }
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
//查询我的
//        query.addWhereEqualTo("uid", BmobUser.getCurrentUser(mContext, UserInfo.class).getObjectId());
        BmobQuery<PlanInfo> eq1 = new BmobQuery<PlanInfo>();
        eq1.addWhereEqualTo("originalPlanId", "empty");
        BmobQuery<PlanInfo> eq2 = new BmobQuery<PlanInfo>();
        //必须是公开的计划
        eq2.addWhereEqualTo("ispublie", 0);
        //分类查询
        BmobQuery<PlanInfo> eq3 = new BmobQuery<PlanInfo>();
        eq3.addWhereEqualTo("category", mCategory);
        List<BmobQuery<PlanInfo>> queries = new ArrayList<BmobQuery<PlanInfo>>();
        //排序
        if (mOrder == 0) {  //时间最新
            query.order("-createdAt");
        } else if (mOrder == 1) {  //参与最多
            query.order("-dovisition");
        } else if (mOrder == 2) {  //执行最多
            query.order("-hadDotimes");
        } else if (mOrder == 3) {  //已完成
            //分类查询
            BmobQuery<PlanInfo> eq4 = new BmobQuery<PlanInfo>();
            eq4.addWhereEqualTo("isDone", 1);
            queries.add(eq4);
        }

        queries.add(eq1);
        queries.add(eq2);
        queries.add(eq3);
        query.and(queries);

//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(mPageSize);
//        query.order("-createdAt");//降序排列
        //分页查询，这个是忽略前mPageSize*curpage条数据
        query.setSkip(mPageSize * mCurPage);
        //还需要将计划相关联的用户信息查询出来哟
        UserInfo user = BmobUser.getCurrentUser(mContext, UserInfo.class);
//        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        //必须要加上这一句表明在查询时也将用户信息给查询出来
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
                    mPlanDataList.clear();
                }
                if (object.size() == mPageSize) {
                    hasMore = true;
                } else {
                    hasMore = false;
                    mDataListView.removeFooterView(mFooterView);
                }

                SerializeUtils.serialization(mContext,Constants.CACHE_HOME_DATA_FILENAME,object);
                mPlanDataList.addAll(object);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                swipeRefreshLayout.setRefreshing(false);
                // TODO Auto-generated method stub
                ToastUtils.showToast(mContext, "查询失败：" + msg);
            }
        });
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.material_red_A700,
                R.color.material_deepOrange_A700,
                R.color.material_indigo_900,
                R.color.material_teal_900);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
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
                swipeRefreshLayout.setEnabled(enable);
            }
        });


    }

    private void initBanner() {
        List<String> datas = Arrays.asList(mPictureUrls);
        mConverBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, datas)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mConverBanner.startTurning(1500);
    }

    @Override
    public void doLoadForCategory(String category) {
        mCategory = category;
        loadData(true);
    }

    @Override
    public void doLoadForOrder(int order) {
        mOrder = order;
        loadData(true);

    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.drawable.message_creategroup_image_named);
            ImageLoader.getInstance().displayImage(data, imageView);
        }
    }


    /**
     * 点击列表下拉箭头操作更多
     */
    private void showOPMoreDialog(final PlanInfo info) {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.home_list_item_op_more, null);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(content);
        int[] size = ScreenUtil.getScreenSize(mContext);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (size[1] * 0.5); // 高度设置为屏幕的0.5
        p.width = (int) (size[0] * 0.85); // 宽度设置为屏幕的0.8
        dialog.getWindow().setAttributes(p);
        dialog.show();

        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String content = view.getText().toString();
                    if ("加入计划".equals(content)) {
                        //参与者+1
                        info.increment("dovisition", 1);
                        //添加多对多关联，表明有一个人加入了这个计划

                        BmobRelation relation = new BmobRelation();
                        //将当前用户添加到多对多关联中
                        relation.add(info);
                        UserInfo user = UserInfo.getCurrentUser(mContext,UserInfo.class);
                        //多对多关联指向`post`的`likes`字段
                        user.mLikes = relation;
                        user.update(mContext, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(mContext,"收藏成功",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(mContext,"收藏失败",Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if ("添加收藏".equals(content)) {
                        //收藏数+1
                        info.increment("favoriteToatl", 1);
                        BmobRelation relation = new BmobRelation();
                        //将当前用户添加到多对多关联中
                        relation.add(info);
                        UserInfo user = UserInfo.getCurrentUser(mContext,UserInfo.class);
                        //多对多关联指向`post`的`likes`字段
                        user.mFavorites = relation;
                        user.update(mContext, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(mContext,"收藏成功",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(mContext,"收藏失败",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }
    }

    /**
     * 创建适配器
     */
    private class MyAdapter extends BaseAdapter {
        List<PlanInfo> datas;
        final String TAG = "MyAdapter";

        public MyAdapter(List<PlanInfo> datas) {
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
                holder.imgGirdView = (GridView) convertView.findViewById(R.id.list_homeplan_img_gridview);
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
            holder.imgGirdView.setAdapter(new GridAdapter(info.picLists));
            //设置用户头像
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(info.author.uimg, holder.mUserIm, defaultOptions);
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
            //点击更多
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOPMoreDialog(info);
                }
            });
            final TextView temp = holder.mZan;
            //点赞单击+1
            holder.mZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info.increment("zanToatl"); // 分数递增1
                    info.update(mContext);
                    //已有赞数+
                    int tota = info.zanToatl + 1;
                    temp.setText(tota + "");
                }
            });
            return convertView;
        }

        private class HViewHolder {
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

    private class GridAdapter extends BaseAdapter {
        private List<String> mPicdatas;

        public GridAdapter(List<String> datas) {
            this.mPicdatas = datas;
        }

        @Override
        public int getCount() {
            return mPicdatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mPicdatas.get(position);
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
            ImageLoader.getInstance().displayImage(mPicdatas.get(position), holder.mImageView, defaultOptions);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }

    /**
     * 这个是适配ListView的效果
     * 定义Item的分隔线，这个分隔线使用的背景是系统提供的，我们可以定制修改，其样式为：
     * <style name="AppTheme" parent="AppBaseTheme">
     * <item name="android:listDivider">@drawable/divider_bg</item>
     * </style>
     * 所以我们修改listDivider的drawable即可
     */
    private class MyItemDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };
        public final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        public final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
        private Drawable mDivider;
        private int mOrientation;

        public MyItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {

            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView v = new RecyclerView(parent.getContext());
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

}
