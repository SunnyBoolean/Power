package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;



import ui.geo.com.power.R;


/**
 * Created by Administrator on 2016/5/17.
 */
public class MaterialDesignGit extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_github);
    }

    @Override
    protected void initListener() {
        super.initListener();
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
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.materoa_design_alert:
                showDIalogM();
                break;
        }
    }

    private void showDIalogM() {
//        dialog.show();
    }


    @Override
    protected void initCompontent() {
        super.initCompontent();
    }
}
