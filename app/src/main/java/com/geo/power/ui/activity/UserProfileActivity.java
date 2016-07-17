package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.geo.com.geo.power.bean.UserInfo;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/7/16.
 * 用户个人资料展示，主要是展示给其他用户看
 */
public class UserProfileActivity extends BaseActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private UserInfo mUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_layout, false);
    }

    private void initData() {
        Intent intent = getIntent();
        mUserInfo = (UserInfo) intent.getSerializableExtra("user");
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        initData();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.user_profile_collapsingToolbarLayout);
        mToolBar = (Toolbar) findViewById(R.id.user_profiletoolbar);
        if(mUserInfo!=null){
            mCollapsingToolbarLayout.setTitle(mUserInfo.getUsername());
            mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
            mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        }
        setSupportActionBar(mToolBar);
//        mToolBar.setNavigationIcon(R.drawable.back); //设置导航按钮，典型的就是返回箭头
        int color = Color.parseColor("#FFFFFF");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.material_white));  //设置标题字体颜色
//        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//
//        });
    }
}
