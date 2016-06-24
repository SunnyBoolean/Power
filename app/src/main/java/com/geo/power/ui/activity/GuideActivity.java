package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.geo.com.geo.power.Constants;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/24.
 * 引导界面，当用户是第一次使用时才会出现此界面
 */
public class GuideActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout,false);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        View view = findViewById(R.id.guide);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,LoginActivity.class);
                startActivity(intent);
                //在使用引导界面并且点击立即坦言后就将此值修改，表示不是第一次使用
                mSharedPreference.edit().putBoolean(Constants.SP_KEY_ISFIRSTUSE,false).commit();
                finish();
            }
        });

    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
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
}
