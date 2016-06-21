package com.geo.power.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/21.
 * 我的收藏
 */
public class MyFavoriteActivity extends BaseActivity {
    private ListView mListView;
    private FAdapter mAdapter;
    private List<PlanInfo> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfavorite_layout);
    }

    @Override
    protected void initCompontent() {
        super.initCompontent();
        mListView = (ListView) findViewById(R.id.myfavorite_list_view);
        initData();
    }

    private void initData() {
        if (mDatas == null) {
            mDatas = new ArrayList<PlanInfo>();
        } else {
            mDatas.clear();
        }
        mAdapter = new FAdapter(mDatas);
        mListView.setAdapter(mAdapter);

        for(int i=0;i<20;i++){
            mDatas.add(new PlanInfo());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showOPMoreDialog();
                return false;
            }
        });
    }
    private void showOPMoreDialog() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.myfavorite_item_op_more, null);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(content);
        int[] size = ScreenUtil.getScreenSize(mContext);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (size[1] * 0.5); // 高度设置为屏幕的0.5
        p.width = (int) (size[0] * 0.85); // 宽度设置为屏幕的0.8
        dialog.getWindow().setAttributes(p);
        dialog.show();

        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, view.getText().toString(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        }
    }

    private class FAdapter extends BaseAdapter {
        private List<PlanInfo> mData;
        public FAdapter(List<PlanInfo> datas){
            this.mData = datas;
        }
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
            convertView = View.inflate(mContext,R.layout.item_myfavorite_list,null);
            return convertView;
        }
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("收藏");
    }
}
