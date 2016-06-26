package com.geo.com.geo.power.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/6/14.
 *  这个是计划更新表，每一次计划更新时就在此插入数据
 */
public class PlanHistoryInfo extends BmobObject {
    public String doTime;

    /** 用户id*/
    public String uid;
    /** 更新的内容*/
    public String content;
    /** 更新的图片，地址*/
    public List<String> picLists = new ArrayList<String>();
    /** 发表计划的位置名称*/
    public String locationArrd;
    /** 发表计划的位置的经度*/
    public double curLongitude;
    /** 发表计划的位置的纬度*/
    public double curLatitude;
}
