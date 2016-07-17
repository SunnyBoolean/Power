package com.geo.com.geo.power.bean;

import android.content.Context;

import com.github.lazylibrary.util.DateUtil;
import com.google.gson.Gson;

/**
 * Created by 李伟 on 2016/7/17.
 * 推送的实体类
 */
public class PushInfo {
    /**
     * 推送的code
     */
    public int push_code;
    /**
     * 推送的内容
     */
    public String push_msg = "";
    /**
     * 推送的用户名
     */
    public String push_username = "";
    /**
     * 推送者的id
     */
    public String push_uid = "";
    /**
     * 推送的时间
     */
    public String push_time = "";

    /**
     * 生成一种推送的json文本
     *
     * @param context  上下文
     * @param pushCode 推送码
     * @param msg      要推送的消息
     * @return
     */
    public static String beanToJson(Context context, int pushCode, String msg) {
        PushInfo info = new PushInfo();
        UserInfo userInfo = UserInfo.getCurrentUser(context, UserInfo.class);
        info.push_code = pushCode;
        info.push_msg = msg;
        info.push_time = DateUtil.getCurDateOnlyDay();
        info.push_username = userInfo.getUsername();
        info.push_uid = userInfo.getObjectId();
        Gson gson = new Gson();
        return gson.toJson(info);
    }

    /**
     * 将要推送的消息转换成实体类
     *
     * @param json
     * @return
     */
    public static PushInfo jsonToBean(String json) {
        PushInfo info = new PushInfo();
        Gson gson = new Gson();
        return gson.fromJson(json, PushInfo.class);
    }
}
