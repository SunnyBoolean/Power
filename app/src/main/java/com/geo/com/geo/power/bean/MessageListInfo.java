package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/6/13.
 * 通知消息列表
 */
public class MessageListInfo  extends BmobObject{
    /** 消息人*/
    public String user="";
    /** 推送時間*/
    public String time = "";
    /** 推送的內容*/
    public String content="";
    /** 推送的类别*/
    public int type;
    /** 推送的标签*/
    public String label="";
    /** 是否已读，0已读，1未度*/
    public int isWatched=1;
}
