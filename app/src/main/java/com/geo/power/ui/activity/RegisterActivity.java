package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.DensityUtil;
import com.rey.material.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/22.
 * 新用户注册
 */
public class RegisterActivity extends BaseActivity {
    private EditText mPhoneNumberEt, mYzmEt;
    private Button mSendYzm, mNextBtn;
    /**
     * 传递过来的值
     */
    public final static String I_KEY = "extra_data";
    /**
     * 注册
     */
    public final static int REGISEN = 0;
    /**
     * 忘记密码
     */
    public final static int FORGET_PASWD = 1;
    private int mExtra = 0;
    private String mPhone;
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

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
        Intent intent = getIntent();
        mExtra = intent.getIntExtra(I_KEY, 0);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSendYzm.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        final android.widget.EditText phoneNumEt = mPhoneNumberEt.getInputEditText();
        //获取电话输入框，当有输入时才允许发送验证码
        phoneNumEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
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
                if (count == 11)
                    mSendYzm.setEnabled(true);

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
                mPhone = mPhoneNumberEt.getText().toString().trim().replaceAll(" ", "");
                //开始发送验证码
                sendSms();
                final int[] time = {120};
                mSendYzm.setEnabled(false);
                mSendYzm.setText(120 + "秒");
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        time[0]--;
                        mSendYzm.setText(time[0] + "秒");
                        if (time[0] < 0) {
                            mSendYzm.setText("重新发送");
                            mSendYzm.setEnabled(true);
                        }
                    }
                };
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                        if (time[0] <= 0) {
                            timer.cancel();
                        }
                    }
                }, 1000, 1000);
                break;
            case R.id.register_next_btn:  //下一步
                mNextBtn.setEnabled(true);
                //读取验证码
                String yzm = mYzmEt.getText().toString().replace(" ", "").trim();
                //发送验证码,如果验证成功会在回调里进行跳转到NextActivity
                SMSSDK.submitVerificationCode("86", mPhone, yzm);
                //暂时测试用，之后删除
                Intent intent = new Intent(mContext, RegisterNextActivity.class);
                intent.putExtra("phoneNum", mPhone);
                startActivity(intent);
                break;
        }
    }

    private void sendSms() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int result = msg.arg2;
                int event = msg.arg1;
                Object data = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        showToast("验证成功！");
                        Intent intent = new Intent();
                        if (mExtra == FORGET_PASWD) {
                            intent.setClass(mContext, ForgetPasswordActivity.class);
                        } else if (mExtra == REGISEN) {
                            intent.setClass(mContext, RegisterNextActivity.class);
                        }
                        intent.putExtra("phoneNum", mPhone);
                        startActivity(intent);
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        showToast("获取验证码成功！");
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        //请求获取验证码
        SMSSDK.getVerificationCode("86", mPhone);

    }




    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();

    }

    protected void onDestroy() {
        // 销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
