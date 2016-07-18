package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/6/15.
 * 用户信息实体类
 */
public class UserInfo extends BmobUser{
    /** 当前所在的纬度*/
    public double Latitude;
    /** 当前所在的经度*/
    public double mLongitude;
    /** 用户昵称*/
    public String unick="";
    /** 用户头像地址*/
    public String uimg = "";
    /** 性别*/
    public String sex="已隐藏";
    /** 年龄*/
    public Number age=0;
    /** 职业*/
    public String profession="";
    /** 能量值*/
    public Number powerValue = 10;
    /** 创建计划的总数*/
    public Number createPlanTotal=0;
    /** 完成的计划的总数*/
    public Number donePlanTotal=0;

    public BmobGeoPoint mGpsAdd;
    /** 添加计划，关联到计划表*/
    public BmobRelation mLikes;
    /** 收藏计划，关联到计划表*/
    public BmobRelation mFavorites;
    /** 我关注的用户*/
    public BmobRelation mAttention;

    /** 关注我的用户*/
    public BmobRelation mAttentionEd;
}
