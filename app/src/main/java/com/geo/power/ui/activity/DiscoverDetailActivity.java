package com.geo.power.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;
import java.util.List;

import app.geo.com.viewflow.CircleFlowIndicator;
import app.geo.com.viewflow.ViewFlow;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/11.
 */
public class DiscoverDetailActivity extends BaseActivity {
    private ConvenientBanner mConverBanner;
    public static final String[] mPictureUrls = {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_detail_layout,false);
    }


    @Override
    protected void initCompontent() {
        super.initCompontent();
        mConverBanner = (ConvenientBanner) findViewById(R.id.home_convenientBanner);
        //初始化广告栏滚动
        initBanner();
    }

    private void initBanner() {
        List<String> datas = Arrays.asList(mPictureUrls);
        mConverBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, datas)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mConverBanner.startTurning(3000);
    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.drawable.message_creategroup_image_named);
            ImageLoader.getInstance().displayImage(data, imageView);
        }
    }
    @Override
    protected void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        super.initToolBar();
    }
}
