package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

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

    public static HomeDongtaiFragment getInstance() {
        if (mInstance == null) {
            mInstance = new HomeDongtaiFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.home_dongtai_layout, null);
        mEmptyVie = content.findViewById(R.id.home_dongtai_empty);
        mDataListView = (ListView) content.findViewById(R.id.mhome_dongtai_rfshlistview);
        mSwipeRefreshView = (SwipeRefreshLayout) content.findViewById(R.id.home_dongtai_swipeLayout);
        initData();
        initSwipeRefresh();
        return content;
    }

    private void initData() {
        mDataListView.setEmptyView(mEmptyVie);
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

}
