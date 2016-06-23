package com.geo.power.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.geo.com.geo.power.bean.UserInfo;
import com.rey.material.widget.EditText;

import cn.bmob.v3.listener.SaveListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class LoginActivity extends BaseActivity {
    private ImageButton mSinaLoginBt, mWechatLoginBt, mQQLoginBt;
    private Button mLoginBtnSend;
    private TextView mRegisterBtn;
    private String mUserName, mPaswd;
    private EditText musernameEt, mPaswdEt;

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
        mLoginBtnSend = (Button) findViewById(R.id.login_btn_send);
        mRegisterBtn = (TextView) findViewById(R.id.login_register_btn);
        musernameEt = (EditText) findViewById(R.id.login_uname_apo);
        mPaswdEt = (EditText) findViewById(R.id.login_asx_password);
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
            case R.id.sina_login:   //微博登录
//                startActivity(intent);
//                finish();
                break;
            case R.id.wechat_login:  //微信登录
//                startActivity(intent);
//                finish();
                break;
            case R.id.qq_login:  //qq登录
//                startActivity(intent);
//                finish();
                break;
            case R.id.login_btn_send:  //点击登录
                login();
//                finish();
                break;
            case R.id.login_register_btn://注册
                intent.setClass(mContext, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        mUserName = musernameEt.getText().toString().trim();
        mPaswd = mPaswdEt.getText().toString().trim();
        UserInfo user = new UserInfo();
        user.setUsername(mUserName);
        user.setPassword(mPaswd);
        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSinaLoginBt.setOnClickListener(this);
        mQQLoginBt.setOnClickListener(this);
        mWechatLoginBt.setOnClickListener(this);
        mLoginBtnSend.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }
}
