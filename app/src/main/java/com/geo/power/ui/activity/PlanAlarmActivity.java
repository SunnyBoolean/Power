package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/7/5.
 * 任务提醒界面
 */
public class PlanAlarmActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.plan_alarm_layout,false);
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

    @Override
    protected void initListener() {
        super.initListener();
    }
}
