package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.geo.com.geo.power.bean.UserInfo;
import com.rey.material.widget.EditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/23.
 * 找回密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    private EditText mNickEt, mNewPaswdEt, mCorPaswdEt;
    private Button mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_paswd_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mNickEt = (EditText) findViewById(R.id.forgetpaswd_usernick_input);
        mNewPaswdEt = (EditText) findViewById(R.id.forgetpaswd_asd_password);
        mCorPaswdEt = (EditText) findViewById(R.id.forgetpaswd_asd_password_confirm);
        mSendButton = (Button) findViewById(R.id.forgetpaswd_next_btn_send);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSendButton.setOnClickListener(this);
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
            case R.id.forgetpaswd_next_btn_send:  //修改密码
                String name = mNickEt.getText().toString();
                String paswd = mCorPaswdEt.getText().toString();
                if(checkRegis()){
                    updatePaswd(name,paswd);
                }
                break;
        }
    }

    /**
     * 找回用户密码，找回用户密码实际上是对账户进行更新，而不是直接修改密码，这两者是有区别的
     */
    private void updatePaswd(String uname, final String paswd) {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("username", uname);
        query.findObjects(mContext, new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                if (object == null || object.size() <= 0) {
                    showToast("该账户不存在");
                    return;
                }
                UserInfo newUser = object.get(0);
                newUser.setPassword(paswd);
                newUser.update(mContext, newUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent();
                        intent.setClass(mContext, LoginActivity.class);
                        startActivity(intent);
                        showToast("设置成功，请重新登录");
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showToast("设置失败");
                    }
                });
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    /**
     * 检查两次密码输入是否一致
     *
     * @return
     */
    private boolean checkRegis() {
        String paswd = mNewPaswdEt.getText().toString().trim();
        String cpaswd = mCorPaswdEt.getText().toString().trim();
        if (TextUtils.isEmpty(paswd)) {
            showToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(cpaswd)) {
            showToast("确认密码不能为空");
            return false;
        }
        if (!paswd.equals(cpaswd)) {
            showToast("两次密码输入不一致");
            return false;
        }
        return true;
    }
}
