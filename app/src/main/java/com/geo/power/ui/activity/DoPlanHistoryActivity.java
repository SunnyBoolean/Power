package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.PlanHistoryInfo;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.power.ui.fragment.DonePlanFragment;
import com.geo.power.ui.fragment.JoinPlanFragment;
import com.geo.power.ui.fragment.MyPlanFragment;
import com.geo.power.ui.fragment.MyPlanRecordFragment;
import com.geo.power.ui.fragment.MyPlanVRecordFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/14.
 * 计划执行的历史记录
 */
public class DoPlanHistoryActivity extends BaseActivity {
    private final String[] mTabTitle = {"我的动态", "参与者动态"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PlanInfo mPlanInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplan_my_recortd);

    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        initData();
        mViewPager = (ViewPager) findViewById(R.id.plan_history_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.plan_history_tablayout);

        //设置Tab的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[1]));
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(MyPlanRecordFragment.getInstance());
        list.add(MyPlanVRecordFragment.getInstance());
        FragmentManager fm = getSupportFragmentManager();
        PlanFragmentAdapter adapter = new PlanFragmentAdapter(fm, list, mTabTitle);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initData() {
        Intent intent = getIntent();
        mPlanInfo = (PlanInfo) intent.getSerializableExtra("planinfo");
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
        mToolBar.setTitle("历史动态");
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
            Fragment fm = list_fragment.get(position);
            Bundle bundle = new Bundle();
            bundle.putCharSequence("planinfo", mPlanInfo.getObjectId());
            fm.setArguments(bundle);
            return fm;
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
