package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.widgets.Dialog;

import ui.geo.com.power.R;


/**
 * Created by Administrator on 2016/5/17.
 */
public class MaterialDesignGit extends BaseActivity {
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_github);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mButton.setOnClickListener(this);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    protected void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.materoa_design_alert:
                showDIalogM();
                break;
        }
    }

    private void showDIalogM() {
        final Dialog dialog = new Dialog(mContext, "提示", "系统将会退出");
        //取消
        dialog.addCancelButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        dialog.show();
    }


    @Override
    protected void initCompontent() {
        mButton = (Button) findViewById(R.id.materoa_design_alert);
        super.initCompontent();
    }
}
