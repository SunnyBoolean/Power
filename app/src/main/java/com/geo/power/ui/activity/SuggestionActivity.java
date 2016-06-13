package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rey.material.widget.EditText;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/13.
 * 意见反馈
 */
public class SuggestionActivity extends BaseActivity{
    private EditText mInputSuggestionEt;
    private Button mSubbmitBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mInputSuggestionEt = (EditText) findViewById(R.id.suggestion_et_fankui);
    mSubbmitBt = (Button) findViewById(R.id.suggestion_subbmit);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("意见反馈");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSubbmitBt.setOnClickListener(this);
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        if(v.getId() == R.id.suggestion_subbmit) {
            String content = mInputSuggestionEt.getText().toString();
            showToast(content);
        }
    }
}
