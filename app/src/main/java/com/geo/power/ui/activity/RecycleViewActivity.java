package com.geo.power.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/17.
 */
public class RecycleViewActivity extends BaseActivity {
    private RecyclerView mRecycleView;
    private MyAdapter mAdapter;
    private List<String> mData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_layout);

    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("RecycleView");
    }

    @Override
    protected void initCompontent() {
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerList);
        initRecycleview();
        super.initCompontent();
    }

    private void initData() {
        mData.clear();
        for (int i = 0;
             i < 30;
             i++) {
            mData.add("How are you? Mr 张" + i);
        }
    }

    private void initRecycleview() {
        initData();
        mAdapter = new MyAdapter(mData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //线性管理横向
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //线性管理纵向
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置网格布局
        GridLayoutManager gridManager = new GridLayoutManager(this, 3);
        //瀑布流
        StaggeredGridLayoutManager stageManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(stageManager);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        //添加Item之间的分隔线
        mRecycleView.addItemDecoration(new DividerGridItemDecoration(this));
//        mRecycleView.addItemDecoration(new MyItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     * 定义自己的ViewHolder，此ViewHolder需要继承实现RecycleView的ViewHolder
     */
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTcontext;
        public ImageView mImagheView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTcontext = (TextView) itemView.findViewById(R.id.hexValue);
            mImagheView = (ImageView) itemView.findViewById(R.id.image_rec_item);
        }
    }

    /**
     * 创建适配器
     */
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<String> datas;
        final String TAG = "MyAdapter";

        public MyAdapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.recycleview_item, viewGroup, false);

            Log.e(TAG, "MyViewHolder()构造器");
            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            myViewHolder.mTcontext.setText(datas.get(i));
            if(i%3==0) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myViewHolder.mImagheView.getLayoutParams();
                params.height= 240;
                myViewHolder.mImagheView.setLayoutParams(params);
            }
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

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
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

    /**
     * 适配Grid和瀑布流分隔线
     */
    public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public DividerGridItemDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

            drawHorizontal(c, parent);
            drawVertical(c, parent);

        }

        private int getSpanCount(RecyclerView parent) {
            // 列数
            int spanCount = -1;
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {

                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                spanCount = ((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount();
            }
            return spanCount;
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getLeft() - params.leftMargin;
                final int right = child.getRight() + params.rightMargin
                        + mDivider.getIntrinsicWidth();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getTop() - params.topMargin;
                final int bottom = child.getBottom() + params.bottomMargin;
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                    int childCount) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager)
                        .getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                    {
                        return true;
                    }
                } else {
                    childCount = childCount - childCount % spanCount;
                    if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                        return true;
                }
            }
            return false;
        }

        private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                                  int childCount) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager)
                        .getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一行，则不需要绘制底部
                    if (pos >= childCount)
                        return true;
                } else
                // StaggeredGridLayoutManager 且横向滚动
                {
                    // 如果是最后一行，则不需要绘制底部
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition,
                                   RecyclerView parent) {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();
            if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
            {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                        mDivider.getIntrinsicHeight());
            }
        }
    }
}
