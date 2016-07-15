package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/11.
 * 发现计划详情
 */
public class DiscoverDetailActivity extends BaseActivity {
    private ImageView mConverBanner;
    private PlanInfo mPlanInfo;
    private TextView mUsernameTv, mLocationTv, mContentTv, mHistoryTotalTv, mVisitorTv, mCommentTv, mCreatetimeTv, mDeadLineTv, mProcessTv;
    private ImageView mUserImgIm;
    private BottomSheetDialog mBottomSheetDialog;
    private CommentAdapter mCommentAdapter;
    private VisitorAdapter mVisitorAdapter;
    private List<CommentInfo> mCommentDatas = new ArrayList<CommentInfo>();
    private List<UserInfo> mVisitorDatas = new ArrayList<UserInfo>();
    private View mCommentContainer;
    private EditText mCommentEt;
    private Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_detail_layout, false);
    }


    @Override
    protected void initCompontent() {
        super.initCompontent();
        initData();
        mConverBanner = (ImageView) findViewById(R.id.home_convenientBanner);
        mLocationTv = (TextView) findViewById(R.id.discover_plan_detail_location);
        mUsernameTv = (TextView) findViewById(R.id.discover_plan_detail_uname);
        mContentTv = (TextView) findViewById(R.id.discover_plan_detail_content);
        mHistoryTotalTv = (TextView) findViewById(R.id.discover_plan_detail_historytotal);
        mUserImgIm = (ImageView) findViewById(R.id.detailplan_uimg);
        mVisitorTv = (TextView) findViewById(R.id.discover_plan_detail_visitortotal);
        mCommentTv = (TextView) findViewById(R.id.discover_plan_detail_commenttotal);
        mCreatetimeTv = (TextView) findViewById(R.id.discover_plan_detail_createtime);
        mDeadLineTv = (TextView) findViewById(R.id.discover_plan_detail_deadlinetime);
        mProcessTv = (TextView) findViewById(R.id.discover_plan_detail_process);
        mCommentContainer = findViewById(R.id.discover_detailplan_cconteims);
        mCommentEt = (EditText) findViewById(R.id.discover_plandetail_commentet);
        mSendBtn = (Button) findViewById(R.id.discover_plandetail_sendbtn);
        UserInfo userinfo = BmobUser.getCurrentUser(mContext, UserInfo.class);
        if (userinfo.getObjectId().equals(mPlanInfo.uid)) {
            mCommentContainer.setVisibility(View.GONE);
        }
        //用户头像
        if(!TextUtils.isEmpty(mPlanInfo.author.uimg)){
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mPlanInfo.author.uimg, mUserImgIm, defaultOptions);
        }
        //内容图片
        if (mPlanInfo.picLists != null && mPlanInfo.picLists.size() > 0) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mPlanInfo.picLists.get(0), mConverBanner, defaultOptions);
        }
        if (TextUtils.isEmpty(mPlanInfo.locationArrd)) {
            mLocationTv.setText("无位置信息");
        } else {
            mLocationTv.setText(mPlanInfo.locationArrd + "");
        }
        mUsernameTv.setText(mPlanInfo.author.getUsername()+"");
        mContentTv.setText(mPlanInfo.content);
        mHistoryTotalTv.setText("动态(" + mPlanInfo.hadDotimes + ")");
        mVisitorTv.setText("参与者(" + mPlanInfo.dovisition + ")");
        mCommentTv.setText("评论(" + mPlanInfo.commentToal + ")");
        mCreatetimeTv.setText("创建时间：" + mPlanInfo.startDate);
        mDeadLineTv.setText("截止时间：" + mPlanInfo.completeDate);

        //计算已经执行了多少天
        int djs = DateUtil.countDays(mPlanInfo.startDate, "yyyy-MM-dd") + 1;
        //计算百分比
        int bfb = 0;
        if (mPlanInfo.plantotalDay != 0) {
            bfb = djs / mPlanInfo.plantotalDay;
        }
        mProcessTv.setText("完成进度：" + bfb + "%");
        mCommentAdapter = new CommentAdapter(mCommentDatas);
        mVisitorAdapter = new VisitorAdapter(mVisitorDatas);
        loadCOmmentData();
        loadVisitor();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mHistoryTotalTv.setOnClickListener(this);
        mVisitorTv.setOnClickListener(this);
        mCommentTv.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    /**
     * 初始化留言板数据
     */
    private void loadCOmmentData() {
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

    private void loadVisitor() {
        // 查询喜欢这个帖子的所有用户，因此查询的是用户表
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("mLikes", new BmobPointer(mPlanInfo));
        query.findObjects(mContext, new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                if (list != null){
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
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);

        switch (v.getId()) {
            case R.id.discover_plan_detail_historytotal:  //历史记录
                Intent intent = new Intent();
                intent.setClass(mContext, DoPlanHistoryActivity.class);
                intent.putExtra("planinfo", mPlanInfo);
                startActivity(intent);
                break;
            case R.id.discover_plan_detail_visitortotal://参与者
                if(mVisitorDatas.size()==0){
                    showSnackBar("还没有人参与此计划！");
                }else{
                    showBottomSheetForVisitor();
                }
                break;
            case R.id.discover_plan_detail_commenttotal: //评论
                if (mCommentDatas.size() == 0) {
                    showSnackBar("还没有人评论！");
                } else {
                    showBottomSheetForComment();
                }
                break;
            case R.id.discover_plandetail_sendbtn: //发送评论
                String comment = mCommentEt.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    showSnackBar("评论内容不能为空！");
                } else {
                    sendComment();
                }
                break;
        }

    }

    private void sendComment() {
        UserInfo user = BmobUser.getCurrentUser(this, UserInfo.class);
        CommentInfo comment = new CommentInfo();
        comment.commentContent = mCommentEt.getText().toString();
        comment.commentTime = DateUtil.getCurDateStr();
        comment.uid = user.getObjectId();

        comment.mPlanInfo = mPlanInfo;
        comment.mUserInfo = user;
        comment.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {

                Toast.makeText(mContext, "评论发表成功", Toast.LENGTH_LONG).show();
                mPlanInfo.increment("commentToal");  //评论总数+1
                mPlanInfo.update(mContext);
                mCommentEt.setText("");
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("评论失败：" + s);
                showSnackBar("评论失败！稍后重试"+s);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mPlanInfo = (PlanInfo) intent.getSerializableExtra("plan_info");
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

    @Override
    protected void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        super.initToolBar();
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
