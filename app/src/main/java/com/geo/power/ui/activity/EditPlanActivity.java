package com.geo.power.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/11.
 */
public class EditPlanActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_plan_activity_layout);
    }

    @Override
    protected void initCompontent() {
        super.initCompontent();
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "完成");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 1) {
                    showToast("发送");
                }
                return false;
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
    }
}
