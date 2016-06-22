package com.geo.power.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.geo.com.geo.power.util.DensityUtil;
import com.rey.material.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/22.
 * 新用户注册
 */
public class RegisterActivity extends BaseActivity {
    private EditText mPhoneNumberEt, mYzmEt;
    private Button mSendYzm, mNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout, false);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mPhoneNumberEt = (EditText) findViewById(R.id.register_phonenumber);
        mYzmEt = (EditText) findViewById(R.id.register_yanzm);
        mSendYzm = (Button) findViewById(R.id.register_getyzmbtn);
        mNextBtn = (Button) findViewById(R.id.register_next_btn);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSendYzm.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        final android.widget.EditText phoneNumEt = mPhoneNumberEt.getInputEditText();
        //获取电话输入框，当有输入时才允许发送验证码
        phoneNumEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        int size = DensityUtil.dip2px(mContext, 18);
        phoneNumEt.setTextSize(size);
        phoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = s.length();
                if (count == 15)
                    mSendYzm.setEnabled(true);
                phoneNumEt.setSelection(s.length());
                if (count == 3) {
                    phoneNumEt.setText(s.toString() + "  ");
                    phoneNumEt.setSelection(phoneNumEt.getText().length());
                } else if (count == 9) {
                    phoneNumEt.setText(s.toString() + "  ");
                    phoneNumEt.setSelection(phoneNumEt.getText().length());
                }
            }
        });
        //验证码，当有验证码时才允许下一步
        android.widget.EditText yzmEt = mYzmEt.getInputEditText();
        yzmEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mNextBtn.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.register_getyzmbtn:  //发送验证码
                final int[] time = {120};
                mSendYzm.setEnabled(false);
                mSendYzm.setText(120 + "秒");
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        time[0]--;
                        mSendYzm.setText(time[0] + "秒");
                    }
                };

                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                        if (time[0] <= 0) {
                            mSendYzm.setEnabled(true);
                            mSendYzm.setText("重新发送");
                            timer.cancel();
                        }
                    }
                }, 1000, 1000);
                break;
            case R.id.register_next_btn:  //下一步
                mNextBtn.setEnabled(true);
                break;
        }

    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();

    }
}
