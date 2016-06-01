package com.geo.power.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gc.materialdesign.views.Slider;
import com.geo.power.ui.fragment.MyPlanCommonOnFragment;
import com.geo.power.ui.fragment.MyPlanDoingFragment;
import com.geo.power.ui.fragment.MyPlanVisitorFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/31.
 * 我的计划详情
 */
public class MyPlanDetailActivity extends BaseActivity{
    private GridView mPicGridView;
    private final String[] mTabTitle = {"历史状态", "为我加油", "参与者"};
    private TabLayout mTabLayout;
    private Slider mPlanProgress;
    private ViewPager mViewPager;
    public static final String[] mPictureUrls = {
            "http://ac-6ptjoad9.clouddn.com/3MekCrFaIezGOmrmbmvkILWjyF2dGIItve4AYXQC",
            "http://ac-6ptjoad9.clouddn.com/aEealv8tKqUxuSn3DHhHKPUQUtkUoVdZcwqN8i9y",
            "http://ac-6ptjoad9.clouddn.com/Itzvdzj3QZG5zR9dNMmPVAZNNviQt5tzJEsaU6jW",
            "http://ac-6ptjoad9.clouddn.com/Itzvdzj3QZG5zR9dNMmPVAZNNviQt5tzJEsaU6jW",
            "http://ac-6ptjoad9.clouddn.com/Q61AFSIOoOipiucsdR8NctVIvkSHimJK9RIZWlnh"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplan_detail);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mPicGridView = (GridView) findViewById(R.id.home_myplan_detail_img_gridview);
        mTabLayout = (TabLayout) findViewById(R.id.myplan_detail_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.myplan_detail_viewpager);
        mPlanProgress = (Slider) findViewById(R.id.plan_progress_slide);
        //计划图片适配器
        mPicGridView.setAdapter(new GridAdapter());
//        initTabViewpager();
    }
    private void initTabViewpager(){
        //设置Tab的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[2]));
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(MyPlanDoingFragment.getInstance());
        list.add(MyPlanCommonOnFragment.getInstance());
        list.add(MyPlanVisitorFragment.getInstance());
        FragmentManager fm = getSupportFragmentManager();
        PlanFragmentAdapter adapter = new PlanFragmentAdapter(fm, list, mTabTitle);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
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
    protected void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }

    /**
     * 三个Fragment：
     */
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
     * 图片适配器
     */
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
}
