package com.geo.power.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.power.ui.activity.ImageShowActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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
    private TextView mCategoryTvContent, mListOrderTvContent;
    private FrameLayout mCategoryTv, mListOrderTv;
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
        mCategoryTvContent = (TextView) content.findViewById(R.id.main_home_category_choice_content);
        mListOrderTvContent = (TextView) content.findViewById(R.id.main_home_list_order_content);
        mCategoryTv = (FrameLayout) content.findViewById(R.id.main_home_category_choice);
        mListOrderTv = (FrameLayout) content.findViewById(R.id.main_home_list_order);
        initCompontent();
        return content;
    }

    private void initCompontent() {
        //初始化导航栏
        initNavigation();
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
        //添加Item之间的分隔线
        mRecycleView.addItemDecoration(new MyItemDecoration(mContext, LinearLayoutManager.VERTICAL));

    }

    private void initNavigation() {
        mCategoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowForCategory();
            }
        });
        mListOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowForListOrder();
            }
        });
    }

    private void popupWindowForCategory() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.home_main_category_menu, null);
        final PopupWindow popwindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popwindow.dismiss();
                    return true;
                }

                return false;
            }
        });
        int[] size = ScreenUtil.getScreenSize(mContext);
        popwindow.setWidth(size[0] / 2);
        popwindow.setTouchable(true);
        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
        popwindow.showAsDropDown(mCategoryTv);

        int childs = content.getChildCount();
        for(int i=0;i<childs;i++){
           final TextView view = (TextView) content.getChildAt(i);
            String text = view.getText().toString();
            if(mCategoryTvContent.getText().toString().equals(text)){
                view.setVisibility(View.GONE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        mCategoryTvContent.setText(view.getText().toString());
                    popwindow.dismiss();
                }
            });
        }
    }
    private void popupWindowForListOrder() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.home_main_clistorder_menu, null);
        final PopupWindow popwindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popwindow.dismiss();
                    return true;
                }

                return false;
            }
        });
        int[] size = ScreenUtil.getScreenSize(mContext);
        popwindow.setWidth(size[0] / 2);
        popwindow.setTouchable(true);
        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
        popwindow.showAsDropDown(mListOrderTv);

        int childs = content.getChildCount();
        for(int i=0;i<childs;i++){
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListOrderTvContent.setText(view.getText().toString());
                    popwindow.dismiss();
                }
            });
        }
    }

    /**
     * 定义自己的ViewHolder，此ViewHolder需要继承实现RecycleView的ViewHolder
     */
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTcontext;
        public GridView mImagheView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTcontext = (TextView) itemView.findViewById(R.id.list_homeplan_comment);
            mImagheView = (GridView) itemView.findViewById(R.id.list_homeplan_img_gridview);
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
                    startActivity(intent);
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

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return mPictureUrls.length;
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return mPictureUrls[position];
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
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
