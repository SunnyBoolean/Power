package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/20.
 * 发现漂流瓶子
 */
public class DiscoverDreamActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_dream_layout);
    }
    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
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
        mToolBar.setTitle("心愿瓶");
    }
}
