package com.geo.power.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.bean.DreamInfo;
import com.geo.com.geo.power.util.DensityUtil;
import com.geo.com.geo.power.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

import static android.view.View.SCROLL_AXIS_NONE;

/**
 * Created by Administrator on 2016/6/20.
 * 发现漂流瓶子
 */
public class DiscoverDreamActivity extends BaseActivity {
    private ListView mListView;
    private boolean mIsStart = true;
    private int mCurIndex = 0;
    private static final int mLoadDataCount = 100;
    private List<DreamInfo> mDreamDatas;
    private DreamAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_dream_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mListView = (ListView) findViewById(R.id.discoverdream_listview);
        initData();
    }

    private void initData() {
        if (mDreamDatas == null) {
            mDreamDatas = new ArrayList<DreamInfo>();
        } else {
            mDreamDatas.clear();
        }
        mAdapter = new DreamAdapter(mDreamDatas);
        mListView.setDivider(null);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setHorizontalScrollBarEnabled(false);
        mListView.setAdapter(mAdapter);

        // 是否有更多数据，
        boolean hasMoreData = false;

        setLastUpdateTime();

    }

    private void initDataNet() {
        for (int i = 0; i < 23; i++) {
            DreamInfo info = new DreamInfo();
            mDreamDatas.add(info);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
    }

    private class DreamAdapter extends BaseAdapter {
        private List<DreamInfo> mDatas;

        public DreamAdapter(List<DreamInfo> data) {
            mDatas = data;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.discover_dream_list_item, null);
            return convertView;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,DreamDetailActivity.class);
                startActivity(intent);
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
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("心愿瓶");


        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "搜索");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setIcon(R.drawable.add_dream);
        //---------- 对子菜单MenuItem进行响应 ------------
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(mContext,AddDreamPlanActivity.class);
                        startActivity(intent);
//                        Toast.makeText(mContext, "排序", Toast.LENGTH_SHORT).show();
//                        popupWindowForListOrder();
                        break;
                }
                return false;
            }
        });
    }

    private void popupWindowForListOrder() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.home_main_clistorder_menu, null);
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
        popwindow.setWidth(size[0] / 2);
        popwindow.setTouchable(true);
        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
//        popwindow.showAsDropDown(mToolBar);
        int disy = DensityUtil.dip2px(mContext, 80);
        int disx = DensityUtil.dip2px(mContext, 4);
        popwindow.showAtLocation(mToolBar, Gravity.RIGHT | Gravity.TOP, disx, disy);

        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mListOrderTvContent.setText(view.getText().toString());
                    popwindow.dismiss();
                }
            });
        }
    }
}
