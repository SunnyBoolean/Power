package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/7/16.
 * 用户个人资料展示，主要是展示给其他用户看
 */
public class UserProfileActivity extends BaseActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private UserInfo mUserInfo;
    private ImageView mUserImage;
    private TextView mSexTv, mNlzTv, mZyTv, mCjjhTv, mWcjhTv,mAgeTv;
    private boolean mIsSelfe = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_layout, false);
    }

    private void initData() {
        Intent intent = getIntent();
        mUserInfo = (UserInfo) intent.getSerializableExtra("user");
        mIsSelfe = intent.getBooleanExtra("isSelfe",false);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        initData();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.user_profile_collapsingToolbarLayout);
        mUserImage = (ImageView) findViewById(R.id.user_profile_fabBtn);
        mToolBar = (Toolbar) findViewById(R.id.user_profiletoolbar);
        mSexTv = (TextView) findViewById(R.id.user_profile_sex);
        mNlzTv = (TextView) findViewById(R.id.user_profile_nlz);
        mZyTv = (TextView) findViewById(R.id.user_profile_zy);
        mCjjhTv = (TextView) findViewById(R.id.user_profile_yjjh);
        mWcjhTv = (TextView) findViewById(R.id.user_profile_ywcjh);
        mAgeTv = (TextView) findViewById(R.id.user_profile_age);
        setSupportActionBar(mToolBar);
        int color = Color.parseColor("#FFFFFF");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.material_white));  //设置标题字体颜色
        loadData();
    }

    private void loadData() {
        //用户名称
        if (mUserInfo != null) {
            mCollapsingToolbarLayout.setTitle(mUserInfo.getUsername());
            mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
            mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        }
        //加载用户头像
        if (!TextUtils.isEmpty(mUserInfo.uimg)) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mUserInfo.uimg, mUserImage, defaultOptions);
        }
        //用户性别
        mSexTv.setText(mUserInfo.sex);
        //能量值
        mNlzTv.setText(mUserInfo.powerValue+"");
        //职业
        mZyTv.setText(mUserInfo.profession);
        //创建计划总数
        mCjjhTv.setText(mUserInfo.createPlanTotal+"");
        //完成计划总数
        mWcjhTv.setText(mUserInfo.donePlanTotal+"");
        mAgeTv.setText(mUserInfo.age+"");

    }
}
