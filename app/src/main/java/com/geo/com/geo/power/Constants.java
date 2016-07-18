package com.geo.com.geo.power;

/**
 * Created by Administrator on 2016/6/23.
 * 常量类
 */
public final class Constants {
    /** Bmob的appkey值*/
    public static final String BMOB_APP_KEY = "fae99438836d65bd72c90645b3faf2cb";
    /** Mob应用的key值*/
    public static final String MOB_APP_KEY = "1432700cbcfa8";
    /** Mob应用的App Secret值*/
    public static final String MOB_APP_SECRET = "deb251cfb33d859fd0eab0a8ee8163ba";


        /** SharedPreference存储的文件名称*/
    public static final String SP_NAME = "power_sp";
    /** 是否是第一次使用，如果是第一次使用需要引导页面*/
    public static final String SP_KEY_ISFIRSTUSE = "sp_isfirst_use";
    /** 是否登录成功*/
    public static final String SP_KEY_ISLOGINEd = "sp_islogined";
    /** 首页缓存的数据文件名*/
    public static final String CACHE_HOME_DATA_FILENAME = "homedata.cache";
    /** 我的计划缓存数据*/
    public static final String CACHE_MYPLAN_DATA_FILENAME="myplandata.cache";
    /**我参与的计划缓存数据*/
    public static final String CACHE_MYJOINPLAN_DATA_FILENAME="myjoinplandata.cache";


    /**
     * 错误码
     */
    public final static  class ErrorCode{
        //注册时，手机号已经被注册，会返回此错误
        public final static int PHONENUMBER_EXIT = 209;
    }

    /**
     * 收到推送时的code
     */
    public final static class PushCode{
        /**计划被赞之后会收到此通知的code*/
        public final static int PUSH_CODE_ZAN = 101;
        /**计划被评论后收到通知的code*/
        public final static int PUSH_CODE_COMMENT=102;
        /**计划被别人加入时会收到此code*/
        public final static int PUSH_CODE_JOINPLAN=103;
        /**有人帮你实现了心愿时的codecode*/
        public final static int PUSH_CODE_DREAMCOMPL=104;

    }
}
