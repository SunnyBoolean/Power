package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geo.com.geo.power.util.AppManager;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/20.
 * 设置 界面
 */
public class SettingActivity extends BaseActivity {
    private Button mExitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);

    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mExitBtn = (Button) findViewById(R.id.setting_exit_app);
    }
    @Override
    protected void initListener() {
        super.initListener();
        mExitBtn.setOnClickListener(this);
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
            case R.id.setting_exit_app:
                AppManager.exitApp(mContext);
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
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
