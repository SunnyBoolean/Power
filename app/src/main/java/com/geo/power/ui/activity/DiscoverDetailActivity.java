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
import com.geo.com.geo.power.bean.LeaveMsgInfo;
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
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/11.
 */
public class DiscoverDetailActivity extends BaseActivity {
    private ImageView mConverBanner;
    private PlanInfo mPlanInfo;
    private TextView mUsernameTv, mLocationTv, mContentTv, mHistoryTotalTv, mVisitorTv, mCommentTv, mCreatetimeTv, mDeadLineTv, mProcessTv;
    private BottomSheetDialog mBottomSheetDialog;
    private CommentAdapter mAdapter;
    private List<CommentInfo> mCommentDatas = new ArrayList<CommentInfo>();
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
        mContentTv = (TextView) findViewById(R.id.discover_plan_detail_content);
        mHistoryTotalTv = (TextView) findViewById(R.id.discover_plan_detail_historytotal);
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
        if (mPlanInfo.picLists != null && mPlanInfo.picLists.size() > 0) {
            //设置用户头像
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
        mContentTv.setText(mPlanInfo.content);
        mHistoryTotalTv.setText("动态(" + mPlanInfo.hadDotimes + ")");
        mVisitorTv.setText("参与者(" + mPlanInfo.dovisition + ")");
        mCommentTv.setText("鼓励(" + mPlanInfo.commentToal + ")");
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
        mAdapter = new CommentAdapter(mCommentDatas);
        loadCOmmentData();
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
                    mAdapter.notifyDataSetChanged();
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
                break;
            case R.id.discover_plan_detail_commenttotal: //评论
                if (mCommentDatas.size() == 0) {
                    showToast("还没有评论");
                } else {
                    showBottomSheetForMsg();
                }
                break;
            case R.id.discover_plandetail_sendbtn: //发送评论
                String comment = mCommentEt.getText().toString();
                if(TextUtils.isEmpty(comment)){
                    showToast("评论内容不能为空");
                }else{
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
        comment.uid=user.getObjectId();

        comment.mPlanInfo = mPlanInfo;
        comment.mUserInfo = user;
        comment.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {

                Toast.makeText(mContext,"评论发表成功",Toast.LENGTH_LONG).show();
                mPlanInfo.increment("commentToal");  //评论总数+1
                mPlanInfo.update(mContext);
                mCommentEt.setText("");
            }

            @Override
            public void onFailure(int i, String s) {
                    showToast("评论失败："+s);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mPlanInfo = (PlanInfo) intent.getSerializableExtra("plan_info");
    }

    /**
     * 显示新增计划菜单
     */
    private void showBottomSheetForMsg() {
        mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.Material_App_BottomSheetDialog);
        View content = LayoutInflater.from(mContext).inflate(R.layout.msg_myplan, null);
        ListView msgListview = (ListView) content.findViewById(R.id.msg_listview);
        ImageButton imClose = (ImageButton) content.findViewById(R.id.discover_plandetail_closecomment);
        msgListview.setAdapter(mAdapter);
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
}
