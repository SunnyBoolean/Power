package com.geo.com.geo.power.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

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
	/**
	 * 获取当前屏幕截图，包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int[] size = getScreenSize(activity);
		int width = size[0];
		int height = size[1];
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int[] size = getScreenSize(activity);
		int width = size[0];
		int height = size[1];
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}
}
