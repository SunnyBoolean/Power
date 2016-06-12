package com.geo.power.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.geo.com.geo.power.bean.NoteInfo;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/12.
 */
public class NoteDetailActivity extends BaseActivity{
    private TextView mContentTv,mCreatetimeTv;
    private NoteInfo mNoteInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_layout);
        mNoteInfo = (NoteInfo) getIntent().getSerializableExtra("note_detail");
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mContentTv = (TextView) findViewById(R.id.note_detail_content);
        mCreatetimeTv = (TextView) findViewById(R.id.note_detail_createtime);

    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }
}
