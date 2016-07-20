package com.geo.power.ui;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;

import com.geo.com.geo.power.Constants;
import com.geo.com.geo.power.CrashHandler;
import com.geo.power.ui.service.CommonService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/5/18.
 */
public class PowerApplication extends Application {
     public static List<Activity> mActivis = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        initCrashHandler();
        initImageLoader();
        initBmob();
        initHuanxin();

    }

    /**
     * 程序异常捕获初始化
     */
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(this.getApplicationContext());
        // 发送以前没发送的报告(可选)
        crashHandler.sendPreviousReportsToServer();
    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 初始化Bmob和Mob
     */
    private void initBmob() {
        //第一：默认初始化
        Bmob.initialize(this, Constants.BMOB_APP_KEY);
        //Mob短信初始化
        SMSSDK.initSDK(this, Constants.MOB_APP_KEY, Constants.MOB_APP_SECRET);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);
    }

    /**
     * 初始化环信即使通讯
     */
    private void initHuanxin(){
//        EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
////初始化
//        EMClient.getInstance().init(this, options);
////在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
    }


}
