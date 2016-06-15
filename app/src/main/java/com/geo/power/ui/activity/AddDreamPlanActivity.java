package com.geo.power.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geo.com.geo.power.util.ScreenUtil;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Switch;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/3.
 */
public class AddDreamPlanActivity extends BaseActivity{
    private TextView mSelectCategoryContent,mIsPublic;
    private View mSelectCategoryClick;
    private Switch mSwitch;
    private EditText mInputEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dream_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mSelectCategoryContent = (TextView) findViewById(R.id.add_dream_selcategory_content);
        mSelectCategoryClick = findViewById(R.id.add_longplan_selcategory_click);
        mSwitch = (Switch) findViewById(R.id.add_dream_pubprio_switcher);
        mIsPublic = (TextView) findViewById(R.id.add_dreamj_rexdh_cv);
        mInputEt = (EditText) findViewById(R.id.add_dream_inputline_asdxv);

    }

    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch(v.getId()){
            case R.id.add_longplan_selcategory_click:
                showPlanCategoryDialog();
                break;
        }
    }

    /**
     * 显示计划分类的选择Dialog
     */
    private void showPlanCategoryDialog() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.add_dream_selecategory_menu, null);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(content);
        int[] size = ScreenUtil.getScreenSize(mContext);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (size[1] * 0.5); // 高度设置为屏幕的0.5
        p.width = (int) (size[0] * 0.7); // 宽度设置为屏幕的0.8
        dialog.getWindow().setAttributes(p);
        dialog.show();

        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectCategoryContent.setTextColor(mCommonColor);
                    mSelectCategoryContent.setText(view.getText().toString());
                    dialog.dismiss();
                }
            });
        }
    }
    @Override
    protected void initListener() {
        super.initListener();
        mInputEt.setMaxEms(10);
        mSelectCategoryClick.setOnClickListener(this);
        mSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if(checked){
                    mIsPublic.setText("抛向大海");
                    mIsPublic.setTextColor(mCommonColor);
                }else{
                    mIsPublic.setTextColor(Color.GRAY);
                    mIsPublic.setText("自己保留");
                }
            }
        });
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("许愿");
    }
}
