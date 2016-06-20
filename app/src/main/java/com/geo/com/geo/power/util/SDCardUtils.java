package com.geo.com.geo.power.util;
public class SDCardUtils {
	/**
	 * 判断是否有SD卡
	 *
	 * @return true表示有且可用，false表示没有
	 */
	public static boolean isSdCanbeUsed() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
		{
			return true;
		} else {
			return false;
		}
	}

}
