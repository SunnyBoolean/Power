package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/6/14.
 */
public class PlanHistoryInfo extends BmobObject {
    public String planId;
    /** 内容*/
    public String content;
    /** 增加时间*/
    public String doTime;
    public int type = 0;
}
