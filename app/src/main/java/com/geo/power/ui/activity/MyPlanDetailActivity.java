package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.geo.com.geo.power.bean.CommentInfo;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.ScreenUtil;
import com.github.lazylibrary.util.DateUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.app.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/31.
 * 我的计划详情
 */
public class MyPlanDetailActivity extends BaseActivity {
    private GridView mPicGridView;
    private ImageView mContentImg;
    private View mHistoryBtn, mCommentBtn, mVisitorBtn;
    private BottomSheetDialog mBottomSheetDialog;
    private List<CommentInfo> mCommentDatas = new ArrayList<CommentInfo>();
    ;
    private List<UserInfo> mVisitorDatas = new ArrayList<UserInfo>();
    private CommentAdapter mCommentAdapter;
    private PlanInfo mPlanInfo;
    private TextView mHistoryTotalTv, mWcjdTv, mDeatlineTv, mCreateTime, mDetailContentTv, mCommentTotalTv, mVisitorTotalTv;
    private VisitorAdapter mVisitorAdapter;
    private int mHistoryTotal;
    /**
     * 计划是否完成，0表示已完成，1表示未完成，正在进行
     */
    private int mIsDone;

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
        mContentImg = (ImageView) findViewById(R.id.home_myplan_detail_img_gridview);
        mHistoryBtn = findViewById(R.id.edit_plan_history_click);
        mCommentBtn = findViewById(R.id.myplan_detail_mycurrage_item_click);
        mHistoryTotalTv = (TextView) findViewById(R.id.plan_detail_histori);
        mWcjdTv = (TextView) findViewById(R.id.plan_detail_wcjd);
        mDeatlineTv = (TextView) findViewById(R.id.plan_detail_deadftime);
        mCreateTime = (TextView) findViewById(R.id.plan_detail_createtime);
        mDetailContentTv = (TextView) findViewById(R.id.myplan_detail_contaihsd);
        mCommentTotalTv = (TextView) findViewById(R.id.myplan_detail_mycurrage_item_comtotal);
        mVisitorTotalTv = (TextView) findViewById(R.id.myplan_detail_mycurrage_item_cisitor_total);
        mVisitorBtn = findViewById(R.id.myplan_detail_mycurrage_item_cisitor_vis_click);
        initData();
        mCommentAdapter = new CommentAdapter(mCommentDatas);
        mVisitorAdapter = new VisitorAdapter(mVisitorDatas);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mHistoryBtn.setOnClickListener(this);
        mCommentBtn.setOnClickListener(this);
        mVisitorBtn.setOnClickListener(this);
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
                intent.putExtra("planinfo", mPlanInfo);
                startActivity(intent);
                break;
            case R.id.myplan_detail_mycurrage_item_click:  //我收到的鼓励
                if (mCommentDatas.size() != 0) {
                    showBottomSheetForComment();
                } else {
                    showSnackBar("还没有评论");
                }
                break;
            case R.id.myplan_detail_mycurrage_item_cisitor_vis_click:
                if (mVisitorDatas.size() != 0) {
                    showBottomSheetForVisitor();
                } else {
                    showSnackBar("还没有参与者");
                }
                break;
        }
    }

    private void initData() {
        Intent intent = getIntent();
        //默认是未完成的
        mIsDone = intent.getIntExtra("isDone", 1);
        mPlanInfo = (PlanInfo) intent.getSerializableExtra("plan_info");
        //计划图片适配器
//        mPicGridView.setAdapter(new GridAdapter(mPlanInfo.picLists));
        loadPlanInfo();
        loadVisitor();
        initOthers();
        //加载历史动态总数
        loadTotalCount();
        //加载鼓励评论的消息
        loadCommentData();
        if (mPlanInfo.picLists != null && mPlanInfo.picLists.size() > 0) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mPlanInfo.picLists.get(0), mContentImg, defaultOptions);
        }
    }

    private void initOthers() {
        mDetailContentTv.setText("   " + mPlanInfo.content);
        mCreateTime.setText("创建时间：" + mPlanInfo.startDate);
        mDeatlineTv.setText("截止时间：" + mPlanInfo.completeDate);
        //计算已经执行了多少天
        int djs = DateUtil.countDays(mPlanInfo.startDate, "yyyy-MM-dd") + 1;
        //计算百分比
        int bfb = 0;
        if (mPlanInfo.plantotalDay != 0) {
            bfb = djs / mPlanInfo.plantotalDay;
        }
        mWcjdTv.setText("完成进度：" + bfb + "%");
    }

    /**
     * 初始化留言板数据
     */
    private void loadCommentData() {

        BmobQuery<CommentInfo> query = new BmobQuery<CommentInfo>();
        query.addWhereEqualTo("mPlanInfo", new BmobPointer(mPlanInfo));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("mUserInfo");
        query.findObjects(mContext, new FindListener<CommentInfo>() {
            @Override
            public void onSuccess(List<CommentInfo> list) {
                if (list != null) {
                    mCommentDatas.addAll(list);
                    mCommentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询计划信息
     */
    private void loadPlanInfo() {
        //查询评论总数
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
        query.addWhereEqualTo("objectId", mPlanInfo.getObjectId());
        query.findObjects(mContext, new FindListener<PlanInfo>() {
            @Override
            public void onSuccess(List<PlanInfo> list) {
                if (list != null && list.size() > 0) {
                    PlanInfo info = list.get(0);
                    mCommentTotalTv.setText(info.commentToal + " ");
                    mDetailContentTv.setText("" + info.content);
                    mVisitorTotalTv.setText("" + info.dovisition);
                    mCreateTime.setText("创建时间：" + info.startDate);
                    mDeatlineTv.setText("截止时间：" + info.completeDate);
                    //计算已经执行了多少天
                    int djs = DateUtil.countDays(info.startDate, "yyyy-MM-dd") + 1;
                    //计算百分比
                    int bfb = 0;
                    if (info.plantotalDay != 0) {
                        bfb = djs / info.plantotalDay;
                    }
                    mWcjdTv.setText("完成进度：" + bfb + "%");
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 加载历史动态数、评论数、参与者总数
     */
    private void loadTotalCount() {
        //============加载评论数目
        BmobQuery<PlanInfo> query = new BmobQuery<PlanInfo>();
        query.addWhereEqualTo("originalPlanId", mPlanInfo.getObjectId());
        query.count(this, PlanInfo.class, new CountListener() {
            @Override
            public void onSuccess(int count) {
                mHistoryTotal = count;
                mHistoryTotalTv.setText("" + count + " ");

            }

            @Override
            public void onFailure(int code, String msg) {
                showToast("count failure：" + msg);
            }
        });
    }

    private void loadVisitor() {
        // 查询喜欢这个帖子的所有用户，因此查询的是用户表
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("mLikes", new BmobPointer(mPlanInfo));
        query.findObjects(mContext, new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                if (list != null) {
                    mVisitorDatas.addAll(list);
                    mVisitorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 显示评论列表
     */
    private void showBottomSheetForComment() {
        mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.Material_App_BottomSheetDialog);
        View content = LayoutInflater.from(mContext).inflate(R.layout.msg_myplan, null);
        ListView msgListview = (ListView) content.findViewById(R.id.msg_listview);
        ImageButton imClose = (ImageButton) content.findViewById(R.id.discover_plandetail_closecomment);
        msgListview.setAdapter(mCommentAdapter);
        int size[] = ScreenUtil.getScreenSize(mContext);
        mBottomSheetDialog.heightParam(size[1] * 2 / 3);
        mBottomSheetDialog.contentView(content)
                .show();
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
    }

    /**
     * 显示参与者列表
     */
    private void showBottomSheetForVisitor() {
        mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.Material_App_BottomSheetDialog);
        View content = LayoutInflater.from(mContext).inflate(R.layout.discover_plan_visitor, null);
        ListView msgListview = (ListView) content.findViewById(R.id.discover_plan_visitor_listview);
        ImageButton imClose = (ImageButton) content.findViewById(R.id.discover_plandetail_closejoin);
        msgListview.setAdapter(mVisitorAdapter);
        int size[] = ScreenUtil.getScreenSize(mContext);
        mBottomSheetDialog.heightParam(size[1] * 2 / 3);
        mBottomSheetDialog.contentView(content)
                .show();
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
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
                        intent.putExtra("plan_info", mPlanInfo);
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
        private List<String> pics;

        public GridAdapter(List<String> mpics) {
            this.pics = mpics;
        }

        @Override
        public int getCount() {
            return pics.size();
        }

        @Override
        public Object getItem(int position) {
            return pics.get(position);
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
            ImageLoader.getInstance().displayImage(pics.get(position), holder.mImageView, defaultOptions);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }

    /**
     * 评论适配器
     */
    private class CommentAdapter extends BaseAdapter {
        private List<CommentInfo> msgDatas;

        public CommentAdapter(List<CommentInfo> data) {
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
                holder.uImg = (ImageView) convertView.findViewById(R.id.dis_comment_plan_item_uimg);

                convertView.setTag(holder);
            } else {
                holder = (MsgViewHolder) convertView.getTag();
            }
            CommentInfo info = msgDatas.get(position);
            holder.contentTv.setText(info.commentContent);
            holder.createtimeTv.setText(info.commentTime);
            holder.usernameTv.setText(info.mUserInfo.getUsername());

            //设置用户头像
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(info.mUserInfo.uimg, holder.uImg, defaultOptions);

            return convertView;
        }

        class MsgViewHolder {
            TextView contentTv, createtimeTv, usernameTv;
            ImageView uImg;
        }
    }

    /**
     * 参与者适配器
     */
    private class VisitorAdapter extends BaseAdapter {
        private List<UserInfo> msgDatas;

        public VisitorAdapter(List<UserInfo> data) {
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
                convertView = View.inflate(mContext, R.layout.discoverplan_visitor_list_item, null);
                holder.sexTv = (TextView) convertView.findViewById(R.id.discoverplan_visitor_item_sex);
                holder.usernameTv = (TextView) convertView.findViewById(R.id.discoverplan_visitor_item_uname);
                holder.uImg = (ImageView) convertView.findViewById(R.id.discoverplan_visitor_item_uimg);
                convertView.setTag(holder);
            } else {
                holder = (MsgViewHolder) convertView.getTag();
            }
            UserInfo info = msgDatas.get(position);
            holder.sexTv.setText("女");
            holder.usernameTv.setText(info.getUsername());

            //设置用户头像
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(info.uimg, holder.uImg, defaultOptions);

            return convertView;
        }

        class MsgViewHolder {
            TextView sexTv, usernameTv;
            ImageView uImg;
        }
    }
}
