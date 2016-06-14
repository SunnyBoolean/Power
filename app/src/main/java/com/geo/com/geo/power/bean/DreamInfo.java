package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/6/14.
 * 心愿实体类
 */
public class DreamInfo extends BmobObject{
    public String id;
    public String content;
    /** 创建时间*/
    public String createtime;
    /** 打开时间*/
    public String deadTime;
    /** 是否已解锁：0表示已解锁，1表示未解锁*/
    public int isUnlock ;
    /** 是否仍向大海：0表示所有人可以看到，1是只有自己保存为私有*/
    public int isSea;
}
