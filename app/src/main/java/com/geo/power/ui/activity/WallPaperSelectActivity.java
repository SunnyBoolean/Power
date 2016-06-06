package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.geo.com.geo.power.bean.WallPaper_Picture;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/5.
 * 选择壁纸背景
 */
public class WallPaperSelectActivity extends BaseActivity {
    private GridView mPaperGridView;
    private ArrayList<String> mData;
    PaperAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_wallpaper_layout);
    }

    @Override
    protected void initCompontent() {
        super.initCompontent();
        toolbar();
        mPaperGridView = (GridView) findViewById(R.id.home_wallpaper_seles_item_img_gridview);
        initData();
    }

    private void initData() {
        mData = new ArrayList<String>();
        BmobQuery<WallPaper_Picture> bmobQuery = new BmobQuery<WallPaper_Picture>();
        bmobQuery.findObjects(mContext, new FindListener<WallPaper_Picture>() {
            @Override
            public void onSuccess(List<WallPaper_Picture> list) {
//                Toast.makeText(mContext, "查询采购", Toast.LENGTH_SHORT).show();
                for (WallPaper_Picture picture : list) {
                    BmobFile file = picture.file_path;
                    String str = file.getFileUrl(mContext);
                    mData.add(str);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "Error:" + s, Toast.LENGTH_LONG).show();
            }
        });
        mPaperGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mData.get(position);
                Intent intent = new Intent();
                intent.putExtra("wallpaper_url", url);
                setResult(12, intent);
                finish();
            }
        });
        mAdapter = new PaperAdapter();
        mPaperGridView.setAdapter(mAdapter);
    }

    private class PaperAdapter extends BaseAdapter {

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
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_select_wallpaper, null);
                holder.mImageV = (ImageView) convertView.findViewById(R.id.wallpaper_selecter_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mData.get(position), holder.mImageV, defaultOptions);

            return convertView;
        }

        class ViewHolder {
            ImageView mImageV;
        }
    }

    private void toolbar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setNavigationIcon(R.drawable.back); //设置导航按钮，典型的就是返回箭头
        int color = Color.parseColor("#FFFFFF");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.material_white));  //设置标题字体颜色
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }
}
