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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.power.ui.activity.DiscoverDetailActivity;
import com.geo.power.ui.activity.ImageShowActivity;
import com.geo.widget.HeaderDecoration;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 * 首页
 */
public class HomeFragment extends BaseFragment {
    private static HomeFragment mInstance;
    private RecyclerView mRecycleView;
    private MyAdapter mAdapter;
    private final String[] mCategorys = {"生活", "健康", "学习", "工作"};
    private ConvenientBanner mConverBanner;
    SwipeRefreshLayout swipeRefreshLayout;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_main_home, null);
        mRecycleView = (RecyclerView) content.findViewById(R.id.home_main_recyclerList);
        swipeRefreshLayout = (SwipeRefreshLayout) content.findViewById(R.id.home_swipeLayout);
        initCompontent();

        return content;
    }

    private void initCompontent() {

        if (mPlanDataList == null) {
            mPlanDataList = new ArrayList<PlanInfo>();
        }
        for (int i = 0; i < 10; i++) {
            mPlanDataList.add(new PlanInfo());
        }
        mAdapter = new MyAdapter(mPlanDataList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        //线性管理横向
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        HeaderDecoration.Builder builder = new HeaderDecoration.Builder(mContext);
        builder.inflate(R.layout.home_recycleview_header);
        builder.parallax(0.2f);
        View container = builder.getContainer();
        mConverBanner = (ConvenientBanner) container.findViewById(R.id.home_convenientBanner);
        initBanner();
        initSwipeRefresh();
    }
    private void initSwipeRefresh(){
        swipeRefreshLayout.setColorSchemeResources(R.color.material_red_A700,
                R.color.material_deepOrange_A700,
                R.color.material_indigo_900,
                R.color.material_teal_900);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);;
//        swipeRefreshLayout.setProgressBackgroundColor(R.color.material_white);
//        swipeRefreshLayout.setPadding(20, 20, 20, 20);
//        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
//        swipeRefreshLayout.setDistanceToTriggerSync(50);
//        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(1,5000);
            }
        });
    }
    android.os.Handler handler = new android.os.Handler(){
        @Override
    public void handleMessage(Message msg){
            swipeRefreshLayout.setRefreshing(false);
        }
    };
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
     * 定义自己的ViewHolder，此ViewHolder需要继承实现RecycleView的ViewHolder
     */
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTcontext;
        public GridView mImagheView;
        public ImageView mMoreIm;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTcontext = (TextView) itemView.findViewById(R.id.list_homeplan_comment);
            mImagheView = (GridView) itemView.findViewById(R.id.list_homeplan_img_gridview);
            mMoreIm = (ImageView) itemView.findViewById(R.id.home_listitem_moreu);
        }
    }

    /**
     * 点击列表下拉箭头操作更多
     */
    private void showOPMoreDialog() {
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
                    Toast.makeText(mContext, view.getText().toString(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        }
    }

    /**
     * 创建适配器
     */
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<PlanInfo> datas;
        final String TAG = "MyAdapter";

        public MyAdapter(List<PlanInfo> datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.list_home_plan_item, viewGroup, false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DiscoverDetailActivity.class);
                    startActivity(intent);
                }
            });
            Log.e(TAG, "MyViewHolder()构造器");
            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            myViewHolder.mTcontext.setText("鼓励(23)");
            myViewHolder.mImagheView.setAdapter(new GridAdapter());
            myViewHolder.mImagheView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ImageShowActivity.class);
                    intent.putExtra("img_url", mPictureUrls[position]);
                    intent.putExtra("position", position);
                    intent.putExtra("img_urls", mPictureUrls);
                    startActivity(intent);
                }
            });
            myViewHolder.mMoreIm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOPMoreDialog();
                }
            });
            Log.e(TAG, "onBindViewHolder()两个参数的");
        }

        @Override
        public int getItemCount() {
            Log.e(TAG, "getItemCount()=" + datas.size());
            return datas.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
            Log.e(TAG, "onBindViewHolder()三个参数的" + payloads.size() + "  " + payloads.toArray());
        }

        @Override
        public int getItemViewType(int position) {
            Log.e(TAG, "getItemViewType()");
            return super.getItemViewType(position);
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            Log.e(TAG, "setHasStableIds()");
            super.setHasStableIds(hasStableIds);

        }

        @Override
        public long getItemId(int position) {
            Log.e(TAG, "getItemId()");
            return super.getItemId(position);
        }

        @Override
        public void onViewRecycled(MyViewHolder holder) {
            Log.e(TAG, "onViewRecycled()");

            super.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(MyViewHolder holder) {
            Log.e(TAG, "onFailedToRecycleView()");

            return super.onFailedToRecycleView(holder);
        }

    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPictureUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return mPictureUrls[position];
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
            ImageLoader.getInstance().displayImage(mPictureUrls[position], holder.mImageView, defaultOptions);
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
