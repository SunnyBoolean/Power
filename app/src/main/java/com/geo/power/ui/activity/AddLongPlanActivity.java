package com.geo.power.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geo.com.geo.power.util.ScreenUtil;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/3.
 */
public class AddLongPlanActivity extends BaseActivity {
    private View mSelectCategory, mPlanDeadline, mPlanNotify, mPlanJieduan;
    private TextView mSelectCategoryContent;
    private ImageView mCategorySelectegIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_longplan);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mSelectCategory = findViewById(R.id.add_longplan_selcategory_click);
        mSelectCategoryContent = (TextView) findViewById(R.id.add_longplan_selcategory_content);
        mCategorySelectegIV = (ImageView) findViewById(R.id.add_longplan_selcategory_imvtag);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSelectCategory.setOnClickListener(this);
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
            case R.id.add_longplan_selcategory_click:
                popupWindowForPlanTag();
                break;
        }
    }

    private void popupWindowForPlanTag() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.add_longplan_selecategory_menu, null);
        final PopupWindow popwindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popwindow.dismiss();
                    return true;
                }

                return false;
            }
        });
        int[] size = ScreenUtil.getScreenSize(mContext);
        int read = (int) (size[0]*0.6);
        popwindow.setWidth(read);
        popwindow.setTouchable(true);
        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
        popwindow.showAtLocation(findViewById(R.id.addplan_kjkdsa), Gravity.CENTER, 0, 0);
        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final android.widget.TextView view = (android.widget.TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int color = Color.parseColor("#004d40");
                    mSelectCategoryContent.setTextColor(mCommonColor);
                    mSelectCategoryContent.setText(view.getText().toString());
                    mCategorySelectegIV.setVisibility(View.VISIBLE);
                    popwindow.dismiss();
                }
            });
        }
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "发送");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
}
