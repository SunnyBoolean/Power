package com.geo.power.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.Constants;
import com.geo.com.geo.power.util.ImmersedStatusbarUtils;
import com.geo.com.geo.power.util.SystemBarTintManager;
import com.geo.power.ui.PowerApplication;
import com.geo.power.ui.fragment.BaseFragment;
import com.github.lazylibrary.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/13.
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseFragment.TaskCallback {
    public Toolbar mToolBar;
    public Context mContext;
    public Handler mHandler;
    public Executor mExecutor;
    private SystemBarTintManager mTintManager;
    private View mContent;
    public final static String P_TAG = "Power";
    public int mCommonColor = Color.parseColor("#004d40");
    public SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    public SharedPreferences mSharedPreference;
    /**
     * 是否显示Toolbar
     */
    private boolean mIsAddToolbar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerApplication.mActivis.add(this);
        PLog(P_TAG, "父类onCreate()");
        mContext = this;
        if (mToolBar != null) {
            init();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    final public void setContentView(int layoutResID) {
        //如果不需要添加Toolbar就直接调用父类方法完成布局填充
        if (!mIsAddToolbar) {
            mContent = View.inflate(this,layoutResID,null);
            super.setContentView(mContent);
            init();
            return;
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        LinearLayout parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        //首先将Toolbar添加的界面
        ViewGroup toolBarRoot = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.tool_bar, parentLinearLayout, true);
        //然后将内容界面添加到以Toolbar为根布局的界面
        View content = View.inflate(this, layoutResID, toolBarRoot);
        mContent =  content;
        mToolBar = (Toolbar) toolBarRoot.findViewById(R.id.toolbar);
        super.setContentView(content);

        init();
    }

    private void initTint() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        } else {
//            ImmersedStatusbarUtils.initAfterSetContentView(this, mContent);
//            return;
        }

        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setStatusBarTintResource(R.color.material_teal_800);
    }

    public void setStatubarColor(int color) {
        mTintManager.setStatusBarTintResource(color);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * @param layoutResID
     * @param isAddToolbar
     */
    final public void setContentView(int layoutResID, boolean isAddToolbar) {
        mIsAddToolbar = isAddToolbar;
        setContentView(layoutResID);
    }

    /**
     * 初始化组件
     */
    protected void initCompontent() {

    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 开始布局前的初始化
     */
    private void init() {
        mSharedPreference = getSharedPreferences(Constants.SP_NAME,
                Activity.MODE_PRIVATE);
        //实现沉浸式状态栏
        initTint();
        initCompontent();
        initToolBar();
        initListener();
        //创建Handler
        if (mHandler == null)
            mHandler = new TaskHandler();
        //创建线程执行器
        if (mExecutor == null)
            mExecutor = Executors.newCachedThreadPool();
    }

    protected void initListener() {
    }


    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    public void handlOnClickListener(View v) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        handlOnClickListener(v);
    }


    /**
     * 关于Toolbar的操作均在此完成
     */
    protected void initToolBar() {
        if (mToolBar == null) {
            return;
        }
        mToolBar.setNavigationIcon(R.drawable.back); //设置导航按钮，典型的就是返回箭头
        int color = Color.parseColor("#FFFFFF");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.material_white));  //设置标题字体颜色
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }

    /**
     * 处理mHandler发送的消息
     *
     * @param msg
     */
    protected void handlerMessage(Message msg) {

    }

    @Override
    public void handleTask() {

    }

    private class TaskHandler extends Handler {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handlerMessage(msg);
        }
    }

    final public void PLog(String tag, String msg) {
        Log.d(tag, msg);
    }

    final public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    final public String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));
    }

    public void showSnackBar(String content) {
        Snackbar.make(mContent, content, Snackbar.LENGTH_LONG)
                .setAction("关闭", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    /**
     * 获取Dialog进度条
     * @return
     */
    final View getProcessView() {
        return View.inflate(mContext, R.layout.add_plan_progress, null);
    }
}

