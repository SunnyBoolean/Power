package com.geo.power.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.util.ScreenUtil;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Switch;

import java.text.SimpleDateFormat;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/3.
 */
public class AddLongPlanActivity extends BaseActivity {
    private View mSelectCategory, mPlanDeadline, mPlanNotify, mPlanLocation, mPlanJieduan;
    private TextView mSelectCategoryContent, mPlanDeadLineTv, mPlanNotifyShow, mIsPublicTV;
    private ImageView mCategorySelectegIV, mPlanDeadlineIm, mPlanNotifyIm;
    private Switch mIsPublicSwitch;

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
        mPlanDeadline = findViewById(R.id.add_longplan_plandeadline_click);
        mPlanNotify = findViewById(R.id.add_longplan_plannotify_click);
        mPlanDeadLineTv = (TextView) findViewById(R.id.add_longplan_plandeadline__tvshow);
        mPlanDeadlineIm = (ImageView) findViewById(R.id.add_longplan_plandeadline__imgone);
        mPlanNotifyShow = (TextView) findViewById(R.id.add_longplan_plannotify_noftivshow);
        mPlanNotifyIm = (ImageView) findViewById(R.id.add_longplan_plannotify_imgone);
        mPlanLocation = findViewById(R.id.addlongplan_location_click);
        mIsPublicSwitch = (Switch) findViewById(R.id.add_longplan_pubprio_switcher);
        mIsPublicTV = (TextView) findViewById(R.id.add_longplan_pubprio_cv);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSelectCategory.setOnClickListener(this);
        mPlanDeadline.setOnClickListener(this);
        mPlanNotify.setOnClickListener(this);
        mPlanLocation.setOnClickListener(this);
        mIsPublicSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (checked) {
                    mIsPublicTV.setText("公开");
                    mIsPublicTV.setTextColor(mCommonColor);
                } else {
                    mIsPublicTV.setText("私有");
                    mIsPublicTV.setTextColor(Color.GRAY);
                }
            }
        });
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
            case R.id.addlongplan_location_click:  //选择位置
                Intent intent = new Intent(mContext, AddLongPlanLocationActivity.class);
                startActivity(intent);
                break;
            case R.id.add_longplan_selcategory_click:  //选择分类
//          //      popupWindowForPlanTag();
                showPlanCategoryDialog();
                break;
            case R.id.add_longplan_plandeadline_click:  //计划截止时间
                showPlanDeadline();
                break;
            case R.id.add_longplan_plannotify_click:  //定时提醒
                showPlanNotify();
                break;

        }
    }

    private void showPlanNotify() {
        com.rey.material.app.Dialog.Builder builder = new TimePickerDialog.Builder(true ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker, 24, 00) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
//                Toast.makeText(mContext, "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                String time = dialog.getFormattedTime(SimpleDateFormat.getTimeInstance());
                mPlanNotifyShow.setText(time);
                mPlanNotifyIm.setVisibility(View.VISIBLE);
                mPlanNotifyShow.setTextColor(mCommonColor);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("完成")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    /**
     * 选择计划截至时间
     */
    private void showPlanDeadline() {
        com.rey.material.app.Dialog.Builder builder = new DatePickerDialog.Builder(true ? R.style.Material_App_Dialog_DatePicker_Light : R.style.Material_App_Dialog_DatePicker) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                mPlanDeadLineTv.setText(date);
                mPlanDeadLineTv.setTextColor(mCommonColor);
                mPlanDeadlineIm.setVisibility(View.VISIBLE);
//                Toast.makeText(mContext, "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
//                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("完成")
                .negativeAction("取消");

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    /**
     * 显示计划分类的选择Dialog
     */
    private void showPlanCategoryDialog() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.add_longplan_selecategory_menu, null);
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
                    mCategorySelectegIV.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
        }
    }

    /**
     * 暂时无用，之前用于计划的分类选择
     */
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
        int read = (int) (size[0] * 0.6);
        popwindow.setWidth(read);
        popwindow.setTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popwindow.setBackgroundDrawable(cd);
//        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
        popwindow.showAtLocation(findViewById(R.id.addplan_kjkdsa), Gravity.CENTER, 0, 0);
        //设置背景变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
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
}
