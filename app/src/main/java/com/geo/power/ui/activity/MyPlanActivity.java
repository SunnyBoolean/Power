package com.geo.power.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.geo.power.ui.fragment.JoinPlanFragment;
import com.geo.power.ui.fragment.MyPlanFragment;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * 我的计划
 * Created by Administrator on 2016/5/20.
 */
public class MyPlanActivity extends BaseActivity {
    private final String[] mTabTitle = {"计划进行时","计划已完成"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mViewPager = (ViewPager) findViewById(R.id.myplan_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.myplan_tablayout);
        //设置Tab的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[1]));
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(JoinPlanFragment.getInstance());
        list.add(MyPlanFragment.getInstance());
        FragmentManager fm = getSupportFragmentManager();
        PlanFragmentAdapter adapter = new PlanFragmentAdapter(fm,list,mTabTitle);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("我的计划");
    }
    private class PlanFragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> list_fragment;                         //fragment列表
        private String[] list_Title;                              //tab名的列表



        public PlanFragmentAdapter(FragmentManager fm,List<Fragment> list_fragment,String[] list_Title) {
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
}
