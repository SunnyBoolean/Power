package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.geo.com.geo.power.Constants;

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
                //获取是否是第一次使用的key值
                boolean isFirst = mSharedPreference.getBoolean(Constants.SP_KEY_ISFIRSTUSE, true);
                //如果是第一次使用就跳转到Guide界面
                if (isFirst) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, GuideActivity.class);
                    startActivity(intent);
                } else {
                    //是否登录过，如果登录过就不再登录了
                    boolean isLogin = mSharedPreference.getBoolean(Constants.SP_KEY_ISLOGINEd, false);
                    if (isLogin) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                    }
                }
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
