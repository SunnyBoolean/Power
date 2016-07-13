package com.geo.com.geo.power.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/5/20.
 * 计划实体类
 */
public class PlanInfo extends BmobObject {
    /** 用户id*/
    public String uid="";
    /** 计划标题*/
    public String title = "";
    /** 目标所属分类:生活、学习、健康、工作*/
    public String category = "生活";
    /** 计划详细内容*/
    public String content = "";
    /** 计划的内容图片：是一个String数组，因此可以支持多图片*/
    public BmobFile mContentPic;
    /** 图片地址*/
    public List<String> picLists = new ArrayList<String>();
    /** 目标完成日期*/
    public String completeDate="";
    /** 目标开始日期*/
    public String startDate="";
    /** 每天提醒的时间*/
    public String notifyDate="";
    /** 发表计划的位置名称*/
    public String locationArrd="";
    /** 发表计划的位置的经度*/
     public double curLongitude;
    /** 发表计划的位置的纬度*/
    public double curLatitude;
    /**计划是否公开：0表示公开，1表示不公开，默认是公开的*/
    public Integer ispublie = 0;
    /** 被鼓励的数*/
    public Integer commentToal=0;
    /** 被点赞的数目*/
    public Integer zanToatl =0;
    /** 被关注（收藏）的数目*/
    public Integer favoriteToatl =0;
    /** 已执行的次数*/
    public Integer hadDotimes =0;
    /** 参与者人数*/
    public Integer dovisition =0;
    /** 计划的总天数*/
    public Integer plantotalDay =0;
    /** 计划是否已完成 0表示未完成，1表示已完成*/
    public Integer isDone = 0;

    //用户关联，将此计划指向某用户，表明该计划是那个用户创建的
    public UserInfo author;
    /** 用于存储加入的计划*/
    public BmobRelation mLikes;
    /** 用于存储用户收藏的计划*/
    public BmobRelation mFavorite;
//    private
    /** 第一次创建的计划的id*/
    public String originalPlanId="empty";
}
