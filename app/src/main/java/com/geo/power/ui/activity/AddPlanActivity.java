package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/2.
 * 添加计划
 */
public class AddPlanActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplan);
        TextView tyv = (TextView) findViewById(R.id.add_pic);

        tyv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(mContext)
                        .start(AddPlanActivity.this, 1);
            }
        });
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    protected void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }
}
