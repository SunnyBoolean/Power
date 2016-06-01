package com.geo.com.geo.power.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {
	/**
	 * 获取手机屏幕高、宽和密度 :[0] 宽度、 [1]高度 、[2]密度
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenSize(Context context) {
		int[] size = new int[3];
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();

		size[0] = dm.widthPixels; // 屏幕宽（像素，如：480px）
		size[1] = dm.heightPixels; // 屏幕高（像素，如：800px）
		size[2] = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		return size;
	}
}
