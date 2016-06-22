package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/18.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash, false);
        jump();
    }

    private void jump() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 50);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
    }
}
