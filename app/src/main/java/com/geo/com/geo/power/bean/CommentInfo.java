package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 李伟 on 2016/6/26.
 * 鼓励/评论表
 * 评论--用户  1对多
 * 评论--计划  1对多
 */
public class CommentInfo extends BmobObject{
    /** 用于存储是谁鼓励/评论了计划*/
    public UserInfo mUserInfo;
    /** 用户存储所鼓励/评论的计划*/
    public PlanInfo mPlanInfo;
    /** 用户id*/
    public String uid="";
    /**评论时间*/
    public String commentTime="";
    /** 评论内容*/
    public String commentContent="";
}
