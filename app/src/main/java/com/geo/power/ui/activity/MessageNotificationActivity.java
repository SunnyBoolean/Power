package com.geo.power.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.MessageListInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/13.
 * 消息
 */
public class MessageNotificationActivity extends BaseActivity {
    private ListView mListView;
    private boolean mIsStart = true;
    private int mCurIndex = 0;
    private static final int mLoadDataCount = 100;
    private List<MessageListInfo> mData = new ArrayList<MessageListInfo>();
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_notification_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mListView = (ListView) findViewById(R.id.messagenotif_listview);
        initData();
    }

    private void initDataNet() {
        if(mData!=null){
            mData.clear();
        }
        for (int i = 0; i < 18; i++) {
            MessageListInfo info = new MessageListInfo();
            info.content = "泼墨suanny关注了你的计划，并且加入执行，你说怎么办啊hi偶发的撒谎iohi的佛山接你的方式集合就";
            info.label = "计划";
            info.time = "2016-06-13";
            if (i < 6) {
                info.isWatched = 1;
            } else {
                info.isWatched = 0;
            }
            mData.add(info);
        }
        finishRefresh();

        mAdapter.notifyDataSetChanged();
    }

    private void initData() {

        mAdapter = new MessageAdapter();
        mListView.setAdapter(mAdapter);
        // 是否有更多数据，
        boolean hasMoreData = false;
        setLastUpdateTime();
    }

    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("消息");
    }

    private class MessageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MViewHolder holder;

            if (convertView == null) {
                holder = new MViewHolder();
                convertView = View.inflate(mContext, R.layout.item_messaghe_notify_list, null);
                holder.contentTv = (TextView) convertView.findViewById(R.id.message_notify_content);
                holder.timeTv = (TextView) convertView.findViewById(R.id.message_notify_createtime);
                holder.labelTv = (TextView) convertView.findViewById(R.id.message_notify_label);
                holder.isWatchedTv = (ImageView) convertView.findViewById(R.id.message_notify_iswatched);
                convertView.setTag(holder);
            } else {
                holder = (MViewHolder) convertView.getTag();
            }
            MessageListInfo info = mData.get(position);
            holder.contentTv.setText(info.content);
            holder.timeTv.setText(info.time);
            holder.labelTv.setText(info.label);
            if (info.isWatched == 0) {  //已读
                holder.isWatchedTv.setBackgroundResource(R.drawable.msg_watched);
            } else if (info.isWatched == 1) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.msg_notwatched);
                holder.isWatchedTv.setBackgroundResource(R.drawable.msg_notwatched);
            }
            return convertView;
        }

        class MViewHolder {
            TextView contentTv;
            TextView timeTv;
            TextView labelTv;
            ImageView isWatchedTv;
        }

    }

    private void finishRefresh() {
        setLastUpdateTime();
    }
}
