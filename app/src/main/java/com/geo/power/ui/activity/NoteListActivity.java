package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.NoteInfo;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/12.
 */
public class NoteListActivity extends BaseActivity {
    private GridView mGridView;
    private List<NoteInfo> mDatas;
    private NoteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mGridView = (GridView) findViewById(R.id.note_list_gridview);
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<NoteInfo>();
        for(int i=0;i<10;i++){
            NoteInfo info = new NoteInfo();
            info.content = "Hello hao are you，what are u doing.";
            info.createTime = "2016-06-12";
            mDatas.add(info);
        }
        mAdapter = new NoteAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,NoteDetailActivity.class);
                intent.putExtra("note_detail",mDatas.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("我的笔记");
    }

    private class NoteAdapter extends BaseAdapter {

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
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
            NoteViewHolder holder;
            if(convertView == null){
                holder = new NoteViewHolder();
                convertView = View.inflate(mContext,R.layout.item_note_list_gridview,null);
                holder.contentTv = (TextView) convertView.findViewById(R.id.note_list_item_content);
                holder.createTimeTv = (TextView) convertView.findViewById(R.id.note_list_item_ctime);
                convertView.setTag(holder);
            }else{
                holder = (NoteViewHolder) convertView.getTag();
            }
            NoteInfo note = mDatas.get(position);
            holder.contentTv.setText(note.content);
            holder.createTimeTv.setText(note.createTime);
            return convertView;
        }
        private class NoteViewHolder{
            TextView contentTv;
            TextView createTimeTv;
        }

    }
}
