package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.PlanHistoryInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/14.
 * 计划执行的历史记录
 */
public class DoPlanHistoryActivity extends BaseActivity {
    private ListView mHistoryListView;
    private List<PlanHistoryInfo> mData;
    private HistoryAdapter mAdapter;
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
        setContentView(R.layout.doplan_history_layout);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mHistoryListView = (ListView) findViewById(R.id.history_doplan_list);
        initData();

    }

    private void initData() {
        if (mData == null) {
            mData = new ArrayList<PlanHistoryInfo>();
        }
        mAdapter = new HistoryAdapter(mData);
        mHistoryListView.setAdapter(mAdapter);
        for (int i = 0; i < 23; i++) {
            PlanHistoryInfo info = new PlanHistoryInfo();
            info.content = "其实并没有什么事情是吃饭解决不了的，如果一次解决不了，那么久吃两顿。。。其实我并不知道啊";
            info.doTime = "06/07";
            mData.add(info);
        }
        mAdapter.notifyDataSetChanged();
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
     *
     */
    private class HistoryAdapter extends BaseAdapter {
        private List<PlanHistoryInfo> data;
        private String lastStr = "";

        public HistoryAdapter(List<PlanHistoryInfo> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            String[] str = data.get(position).doTime.split("/");
            if (str[0].equals(lastStr)) {
                lastStr = str[0];
                return 0;
            } else {
                lastStr = str[0];
                return 1;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PViewHolder holder = null;
            PlanHistoryInfo info = data.get(position);
            if (convertView == null) {
                holder = new PViewHolder();
                convertView = View.inflate(mContext, R.layout.item_plan_history_list_content_te, null);
                holder.contentTv = (TextView) convertView.findViewById(R.id.plan_history_itrem_content);
                holder.timeTv = (TextView) convertView.findViewById(R.id.plan_history_item_ctime);
                holder.imgGridView = (GridView) convertView.findViewById(R.id.plan_history_item_img_gridview);
                convertView.setTag(holder);
            } else {
                holder = (PViewHolder) convertView.getTag();
            }
            if(position%3!=0){
                holder.imgGridView.setAdapter(new GridAdapter());
                holder.imgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(mContext,ImageShowActivity.class);
                        startActivity(intent);
                    }
                });
            }
            holder.timeTv.setText(info.doTime);
            holder.contentTv.setText(info.content);
            return convertView;
        }

        private class PViewHolder {
            TextView contentTv;
            TextView timeTv;
            GridView imgGridView;
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
    }
}
