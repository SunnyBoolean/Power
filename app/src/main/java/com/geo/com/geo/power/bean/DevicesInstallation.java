package com.geo.com.geo.power.bean;

import android.content.Context;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by 李伟 on 2016/7/17.
 * 设备安装实体类，主要用于推送
 */
public class DevicesInstallation extends BmobInstallation {
    public DevicesInstallation(Context context) {
        super(context);
    }
    /** 用户id*/
    public String uid;
}
