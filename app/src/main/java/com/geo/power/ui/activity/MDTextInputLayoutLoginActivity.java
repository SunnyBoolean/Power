package com.geo.power.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class MDTextInputLayoutLoginActivity extends BaseActivity {
    TextInputLayout mTextInputLayoutUsername, mTextInputLayoutPassword;
    private Button mLoginBtn;
    private EditText mUserNameEt, mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design);
    }

    @Override
    protected void initCompontent() {
        mTextInputLayoutUsername = (TextInputLayout) findViewById(R.id.materialdesign_inputtext_usernmame);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.materialdesign_inputtext_passord);
        mLoginBtn = (Button) findViewById(R.id.login);
        mUserNameEt = (EditText) findViewById(R.id.username);
        mPasswordEt = (EditText) findViewById(R.id.password);
        mTextInputLayoutUsername.setHint("用户名");
        mTextInputLayoutPassword.setHint("密码");
        super.initCompontent();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLoginBtn.setOnClickListener(this);
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.login:
                String username = mUserNameEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                if (TextUtils.isEmpty(username) || username.length() <= 5) {
                    mTextInputLayoutUsername.setErrorEnabled(true);
                    mTextInputLayoutUsername.setError("用户名长度不能小于5");
                }else{
                    mTextInputLayoutUsername.refreshDrawableState();
                }
                if (TextUtils.isEmpty(password) || password.length() <= 6) {
                    mTextInputLayoutPassword.setErrorEnabled(true);
                    mTextInputLayoutPassword.setError("密码长度不能小于6");
                }else{
                    mTextInputLayoutPassword.setErrorEnabled(false);
                }
                break;

        }
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("Material Design");
    }
}

