package com.geo.com.geo.power.bean;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/6/12.
 */
public class NoteInfo extends BmobObject {
    public String content;
    public String createTime;

    /**
     * 判断指定包名的app是否处于正在前台运行
     * @param context
     * @param packageName   运行的程序，也就是导航app的包名
     * @return
     */
    public boolean is(Context context,String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTask = activityManager.getRunningTasks(1);
      //默认不是在运行
        boolean isAppInFront = false;
        if (appTask != null) {
            if (appTask.size() > 0) {
                if (appTask.get(0).topActivity.toString().contains(packageName)) {
                    isAppInFront = true;
                }
            }
        }
        return isAppInFront;
    }
}
