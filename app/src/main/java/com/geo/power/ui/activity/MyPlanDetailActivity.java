package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.LeaveMsgInfo;
import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.power.ui.fragment.MyPlanCommonOnFragment;
import com.geo.power.ui.fragment.MyPlanDoingFragment;
import com.geo.power.ui.fragment.MyPlanVisitorFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.app.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/31.
 * 我的计划详情
 */
public class MyPlanDetailActivity extends BaseActivity {
    private GridView mPicGridView;
    private View mHistoryBtn,mLeaveMsgBtn;
    private BottomSheetDialog mBottomSheetDialog;
    private List<LeaveMsgInfo> mMsgData;
    private MsgAdapter mMsgAdapter;
    public static final String[] mPictureUrls = {
            "http://ac-6ptjoad9.clouddn.com/3MekCrFaIezGOmrmbmvkILWjyF2dGIItve4AYXQC",
            "http://ac-6ptjoad9.clouddn.com/aEealv8tKqUxuSn3DHhHKPUQUtkUoVdZcwqN8i9y",
            "http://ac-6ptjoad9.clouddn.com/Itzvdzj3QZG5zR9dNMmPVAZNNviQt5tzJEsaU6jW",
            "http://ac-6ptjoad9.clouddn.com/Itzvdzj3QZG5zR9dNMmPVAZNNviQt5tzJEsaU6jW",
            "http://ac-6ptjoad9.clouddn.com/Q61AFSIOoOipiucsdR8NctVIvkSHimJK9RIZWlnh"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplan_detail);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mPicGridView = (GridView) findViewById(R.id.home_myplan_detail_img_gridview);
        mHistoryBtn = findViewById(R.id.edit_plan_history_click);
        mLeaveMsgBtn = findViewById(R.id.myplan_detail_mycurrage_item_click);
        //计划图片适配器
        mPicGridView.setAdapter(new GridAdapter());
        initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mHistoryBtn.setOnClickListener(this);
        mLeaveMsgBtn.setOnClickListener(this);
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.edit_plan_history_click:   //计划执行的历史记录
                intent.setClass(mContext, DoPlanHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.myplan_detail_mycurrage_item_click:  //我收到的鼓励
                showBottomSheetForMsg();
                break;
        }
    }

    private void initData() {
        initMsgData();
    }

    /**
     * 初始化留言板数据
     */
    private void initMsgData() {
        if (mMsgData == null) {
            mMsgData = new ArrayList<LeaveMsgInfo>();
        } else {
            mMsgData.clear();
        }
        mMsgAdapter = new MsgAdapter(mMsgData);
        for (int i = 0; i < 20; i++) {
            LeaveMsgInfo info = new LeaveMsgInfo();
            info.content = "你都坚持减肥那么久了，有效果吗？";
            info.createtime = "2016-06-14";
            info.name = "泼墨";
            mMsgData.add(info);
        }
    }

    /**
     * 显示新增计划菜单
     */
    private void showBottomSheetForMsg() {
        mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.Material_App_BottomSheetDialog);
        View content = LayoutInflater.from(mContext).inflate(R.layout.msg_myplan, null);
        ListView msgListview = (ListView) content.findViewById(R.id.msg_listview);
        msgListview.setAdapter(mMsgAdapter);
        int size[] = ScreenUtil.getScreenSize(mContext);
//        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));
//                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
//        Button bt_wrap = (Button)v.findViewById(R.id.sheet_bt_wrap);
//                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
//        });
        mBottomSheetDialog.heightParam(size[1]*2/3);
        mBottomSheetDialog.contentView(content)
                .show();
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "编辑");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setIcon(R.drawable.myplan_continue_edit);
        //---------- 对子菜单MenuItem进行响应 ------------
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(mContext, EditPlanActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 图片适配器
     */
    private class GridAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mPictureUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return mPictureUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_gridview, null);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.item_gridview_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mPictureUrls[position], holder.mImageView, defaultOptions);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }

    private class MsgAdapter extends BaseAdapter {
        private List<LeaveMsgInfo> msgDatas;

        public MsgAdapter(List<LeaveMsgInfo> data) {
            this.msgDatas = data;
        }

        @Override
        public int getCount() {
            return msgDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return msgDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MsgViewHolder holder = null;
            if (convertView == null) {
                holder = new MsgViewHolder();
                convertView = View.inflate(mContext, R.layout.msg_plan_list_item, null);
                holder.contentTv = (TextView) convertView.findViewById(R.id.msg_plan_item_content);
                holder.createtimeTv = (TextView) convertView.findViewById(R.id.msg_plan_item_createtime);
                holder.usernameTv = (TextView) convertView.findViewById(R.id.msg_plan_item_uname);
                holder.uImg = (ImageView) convertView.findViewById(R.id.msg_plan_item_uimg);

                convertView.setTag(holder);
            } else {
                holder = (MsgViewHolder) convertView.getTag();
            }
            LeaveMsgInfo info = msgDatas.get(position);

            holder.contentTv.setText(info.content);
            holder.createtimeTv.setText(info.createtime);
            holder.usernameTv.setText(info.name);
            return convertView;
        }

        class MsgViewHolder {
            TextView contentTv, createtimeTv, usernameTv;
            ImageView uImg;
        }
    }
}
