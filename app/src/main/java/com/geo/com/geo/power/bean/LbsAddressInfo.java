package com.geo.com.geo.power.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Administrator on 2016/6/15.
 */
public class LbsAddressInfo extends BmobObject{
    /** 当前所在的纬度*/
    public double Latitude;
    /** 当前所在的经度*/
    public double mLongitude;
    public BmobGeoPoint mGpsAdd;
}
