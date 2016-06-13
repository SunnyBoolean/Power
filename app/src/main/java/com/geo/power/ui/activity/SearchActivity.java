package com.geo.power.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.util.DensityUtil;
import com.rey.material.widget.Spinner;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/20.
 * 搜索
 */
public class SearchActivity extends BaseActivity {
    private Spinner mSpinner;
    private ListView mHistoryListView;
    private String[] historys = {"跑步","学英语","高等数学","考研","健身","减肥","美食","物理","学习","生活"};
    private ImageButton mImageButtonSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout,false);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mToolBar = (Toolbar) findViewById(R.id.search_toolbar);
        mSpinner = (Spinner) findViewById(R.id.search_spinner_labels);
        mHistoryListView = (ListView) findViewById(R.id.search_history_listview);
        mImageButtonSearch = (ImageButton) findViewById(R.id.start_serarc);
        mHistoryListView.setAdapter(new HistoryAdapter());

        mImageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        final int tsize = DensityUtil.dip2px(mContext, 5);
        String[] items = {"所有", "我的", "", "工作"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        mSpinner.setAdapter(adapter);
        mSpinner.setmShowTextColor(Color.WHITE);
        mSpinner.setShowTextSize(tsize);
    }
    private class HistoryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return historys.length;
        }

        @Override
        public Object getItem(int position) {
            return historys[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext,R.layout.item_serach_history_list,null);
            TextView tv = (TextView) convertView.findViewById(R.id.item_search_history_content);
            tv.setText(historys[position]);
            return convertView;
        }
    }
}
