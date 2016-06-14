package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/6/14.
 * 我的计划留言板实体类
 */
public class LeaveMsgInfo extends BmobObject {
    /** 评论者姓名*/
    public String name;
    /** 评论者id*/
    public String uid;
    /** 计划id*/
    public String planId;
    /** 评论的内容*/
    public String content;
    /** 评论发表的时间*/
    public String createtime;

}
