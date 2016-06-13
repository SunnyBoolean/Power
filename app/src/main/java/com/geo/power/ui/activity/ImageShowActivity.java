package com.geo.power.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import app.geo.com.viewflow.CircleFlowIndicator;
import app.geo.com.viewflow.ViewFlow;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ImageShowActivity extends BaseActivity {
    private ViewFlow viewFlow;
    /**
     * 当前选中的图片的位置
     */
    private int mPosition;
    private String[] mPictures = {
            "http://ac-6ptjoad9.clouddn.com/3MekCrFaIezGOmrmbmvkILWjyF2dGIItve4AYXQC",
            "http://ac-6ptjoad9.clouddn.com/aEealv8tKqUxuSn3DHhHKPUQUtkUoVdZcwqN8i9y",
            "http://ac-6ptjoad9.clouddn.com/Itzvdzj3QZG5zR9dNMmPVAZNNviQt5tzJEsaU6jW",
            "http://ac-6ptjoad9.clouddn.com/Q61AFSIOoOipiucsdR8NctVIvkSHimJK9RIZWlnh",
            "http://ac-6ptjoad9.clouddn.com/9JUZAO5WitlZWbKydQSbNxArhR9miKLc5zY9dE6o",
            "http://ac-6ptjoad9.clouddn.com/Akz36NdJjN2oXkXQYe5GqaE9AA2aocsWYQyzTgfq",
            "http://ac-6ptjoad9.clouddn.com/exVLcXBmU4zxOcBtESLtVJ8IMwLXzUZb8rC4aP1H",
            "http://ac-6ptjoad9.clouddn.com/djAnPc8BxGT7dDh3UAK06OOlG0En3OnLFCTC54cv",
            "http://ac-6ptjoad9.clouddn.com/JHMQOVBhqcQ204ERMBarbYbpT1noqSSiASU4MunW",
            "http://ac-6ptjoad9.clouddn.com/MwGpKcdKiEWPG1yl20TcWuVvf9SCe5VQ6gIPwfNi"
    };

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow_layout, false);
        Intent intent = getIntent();
        String url = intent.getStringExtra("img_url");
        mPosition = intent.getIntExtra("position", 0);
//        mPictures = intent.getStringArrayExtra("img_urls");
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
//        setStatubarColor(Color.TRANSPARENT);
//        super.initToolBar();
//        mToolBar.setVisibility(View.GONE);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        viewFlow.setAdapter(new ImageAdapter(this), mPictures.length);
        CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        viewFlow.setFlowIndicator(indic);
        viewFlow.setSelection(mPosition);
    }


    public class ImageAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public ImageAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mPictures.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.home_image_item, null);
            }
            ImageView im = (ImageView) convertView.findViewById(R.id.home_imgView);
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(mPictures[position], im, defaultOptions);

            return convertView;
        }

    }
}
