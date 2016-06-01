package com.geo.power.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 */
public class DiscoverFragment extends BaseFragment {
    private static DiscoverFragment mInstance;
    private final String[] mTabTitle = {"我的计划", "我参与的", "已完成"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FMCallback mFragmentManagerCallback;

    public static DiscoverFragment getInstance() {
        if (mInstance == null) {
            mInstance = new DiscoverFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = View.inflate(mContext, R.layout.fragment_main_discover, null);
        mViewPager = (ViewPager) content.findViewById(R.id.home_plan_viewpager);
        mTabLayout = (TabLayout) content.findViewById(R.id.home_plan_tablayout);
        initCompontent();
        return content;
    }

    protected void initCompontent() {

        //设置Tab的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[2]));
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(MyPlanFragment.getInstance());
        list.add(JoinPlanFragment.getInstance());
        list.add(DonePlanFragment.getInstance());
        if (mFragmentManagerCallback == null) {
            return;
        }
        FragmentManager fm = getChildFragmentManager();
//        FragmentManager fm = mFragmentManagerCallback.getFragmentM();
        PlanFragmentAdapter adapter = new PlanFragmentAdapter(fm, list, mTabTitle);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class PlanFragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> list_fragment;                         //fragment列表
        private String[] list_Title;                              //tab名的列表


        public PlanFragmentAdapter(FragmentManager fm, List<Fragment> list_fragment, String[] list_Title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.length;
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title[(position % list_Title.length)];
        }
    }

    /**
     * 为了在Fragment中嵌套Fragment获取FragmentManager的回调接口
     *
     * @param fm
     */
    public void setFMCallback(FMCallback fm) {
        this.mFragmentManagerCallback = fm;
    }

    public interface FMCallback {
        public FragmentManager getFragmentM();
    }
}
