package com.geo.power.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class LoginActivity extends BaseActivity {
    private ImageButton mSinaLoginBt, mWechatLoginBt, mQQLoginBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout, false);

    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mSinaLoginBt = (ImageButton) findViewById(R.id.sina_login);
        mWechatLoginBt = (ImageButton) findViewById(R.id.wechat_login);
        mQQLoginBt = (ImageButton) findViewById(R.id.qq_login);
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        Intent intent = new Intent(mContext, MainActivity.class);
        switch (v.getId()) {
            case R.id.sina_login:
                startActivity(intent);
                finish();
                break;
            case R.id.wechat_login:
                startActivity(intent);
                finish();
                break;
            case R.id.qq_login:
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSinaLoginBt.setOnClickListener(this);
        mQQLoginBt.setOnClickListener(this);
        mWechatLoginBt.setOnClickListener(this);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }
}
