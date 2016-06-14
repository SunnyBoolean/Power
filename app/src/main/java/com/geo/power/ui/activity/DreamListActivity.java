package com.geo.power.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.geo.power.ui.fragment.DonePlanFragment;
import com.geo.power.ui.fragment.HadDoneDreamFragmetn;
import com.geo.power.ui.fragment.IDoneDreamFragmetn;
import com.geo.power.ui.fragment.JoinPlanFragment;
import com.geo.power.ui.fragment.MyPlanFragment;
import com.geo.power.ui.fragment.NotDoneDreamFragmetn;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/13.
 * 心愿瓶子
 */
public class DreamListActivity extends BaseActivity{
    private final String[] mTabTitle = {"未开启", "已开启", "我参与的"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dream_list_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mViewPager = (ViewPager) findViewById(R.id.dream_list_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.dream_list_tablayout);
      //设置Tab的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[2]));
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(NotDoneDreamFragmetn.getInstance());
        list.add(HadDoneDreamFragmetn.getInstance());
        list.add(IDoneDreamFragmetn.getInstance());
        FragmentManager fm = getSupportFragmentManager();
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

    @Override
    protected void initListener() {
        super.initListener();
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("心愿瓶子");
    }
}
