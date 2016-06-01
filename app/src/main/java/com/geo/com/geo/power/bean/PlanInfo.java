package com.geo.com.geo.power.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/20.
 * 计划实体类
 */
public class PlanInfo implements Serializable {
    /** 计划标题*/
    public String title = "";
    /** 目标所属分类:生活、学习、健康、工作*/
    public String category = "";
    /** 计划详细内容*/
    public String content = "";
    /** 计划的内容图片：是一个String数组，因此可以支持多图片*/
    public String[] picturesUrl;
    /** 目标完成日期*/
    public Date completeDate;
    /** 目标开始日期*/
    public Date startDate;
    /** 每天提醒的时间*/
    public Date notifyDate;
    /** 发表计划的位置名称*/
    public String locationArrd;
    /** 发表计划的位置的经度*/
     public double curLongitude;
    /** 发表计划的位置的纬度*/
    public double curLatitude;
    /**计划是否公开：0表示公开，1表示不公开，默认是公开的*/
    public int ispublie = 0;
    /** 被评论的数*/
    public int commentToal;
    /** 被点赞的数目*/
    public int zanToatl;
    /** 被关注（收藏）的数目*/
    public int favoriteToatl;
}
