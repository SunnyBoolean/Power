package com.geo.power.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ImageShowActivity extends BaseActivity{
    private ImageView mImageView;
    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow_layout);
        mImageView = (ImageView) findViewById(R.id.image_show_url);
        String url = getIntent().getStringExtra("img_url");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoader.getInstance().displayImage(url,mImageView,defaultOptions);
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setVisibility(View.GONE);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
    }
}
