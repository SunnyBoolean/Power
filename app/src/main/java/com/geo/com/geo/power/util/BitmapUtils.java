package com.geo.com.geo.power.util;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.Display;

/**
 * 图片工具类
 *
 * @author Administrator
 *
 */
public class BitmapUtils {
	/**
	 * 根据Uri获取图片
	 *
	 * @param activity
	 * @param mImageUri
	 * @return
	 */
	public static Bitmap getBitmap(Activity activity, Uri mImageUri) {
		// 获取手机屏幕大小
		Display currentDisplay = activity.getWindowManager()
				.getDefaultDisplay();
		Point point = new Point();
		currentDisplay.getSize(point);
		int dw = point.x;
		int dh = point.y;
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// 加载图片尺寸而不是图像本身
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = null;
		InputStream bmpIs = null;
		try {
			bmpIs = activity.getContentResolver().openInputStream(mImageUri);
			bmp = BitmapFactory.decodeStream(bmpIs, null, bmpFactoryOptions);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);
		// 如果两个比率都大于1
		// 那么图像的一条边将大于屏幕
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// 若高度比率更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// 若宽度比例更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// 对他进行真正的解码
		bmpFactoryOptions.inJustDecodeBounds = false;
		InputStream bmpI = null;
		try {
			bmpI = activity.getContentResolver().openInputStream(mImageUri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bmp = BitmapFactory.decodeStream(bmpI, null, bmpFactoryOptions);
		return bmp;
	}

	/**
	 * 根据图片的Uri获取图片的绝对路径
	 *
	 * @param activity
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getBitmap(Activity activity, String imagePath) {
		// 获取手机屏幕大小
		Display currentDisplay = activity.getWindowManager()
				.getDefaultDisplay();
		Point point = new Point();
		currentDisplay.getSize(point);
		int dw = point.x;
		int dh = point.y;
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// 加载图片尺寸而不是图像本身
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = null;
		bmp = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);

		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);
		// 如果两个比率都大于1
		// 那么图像的一条边将大于屏幕
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// 若高度比率更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// 若宽度比例更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// 对他进行真正的解码
		bmpFactoryOptions.inJustDecodeBounds = false;

		bmp = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);
		return bmp;
	}

	/**
	 * 根据Drawable目录获取 Bitmap
	 *
	 * @param activity
	 * @param resId
	 * @param isJustSize
	 * @return
	 */
	public static Bitmap getBitmap(Activity activity, int resId,
								   boolean isJustSize) {
		// 获取手机屏幕大小
		Display currentDisplay = activity.getWindowManager()
				.getDefaultDisplay();
		Point point = new Point();
		currentDisplay.getSize(point);
		int dw = point.x;
		int dh = point.y;
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// 加载图片尺寸而不是图像本身
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = null;
		bmp = BitmapFactory.decodeResource(activity.getResources(), resId,
				bmpFactoryOptions);
		// 如果只需要尺寸，则直接返回即可
		if (isJustSize == true) {
			return bmp;
		}
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);
		// 如果两个比率都大于1
		// 那么图像的一条边将大于屏幕
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// 若高度比率更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// 若宽度比例更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// 对他进行真正的解码
		bmpFactoryOptions.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeResource(activity.getResources(), resId,
				bmpFactoryOptions);
		return bmp;
	}

	/**
	 * 根据图片uri获取缩略图
	 *
	 * @param context
	 * @param imgUri
	 * @return
	 */
	public static Bitmap getThumbiImg(Context context, String imgUrl) {
		ContentResolver resover = context.getContentResolver();
		// 正常图片的选择列,我们只需要根据真实URI找到id即可
		String[] realProjectino = { Images.Media._ID };
		// 缩略图的选择列
		String[] thumbnailSojection = { Thumbnails.DATA, // 缩略图url
				Thumbnails.HEIGHT, // 缩略图高度
				Thumbnails.WIDTH, // 缩略图宽度
				Thumbnails._ID // 缩略图id
		};
		// 首先根据真实的uri获取到图片id
		Cursor cursor = resover.query(Images.Media.EXTERNAL_CONTENT_URI,
				realProjectino, Images.Media.DATA + " = ?",
				new String[] { imgUrl }, null);
		if (cursor == null && !cursor.moveToFirst()) {
			return null;
		} else {
			cursor.moveToNext();
		}
		int idIndex = cursor.getColumnIndexOrThrow(Images.Media._ID);

		String imageId = cursor.getString(idIndex);
		cursor.close();
		// 然后根据查询到的id找到缩略图
		Cursor cur = resover.query(Thumbnails.EXTERNAL_CONTENT_URI,
				thumbnailSojection, Thumbnails._ID + "= ?",
				new String[] { imageId }, null);
		if (cur == null && !cur.moveToFirst()) {
			return null;
		} else {
			cur.moveToNext();
		}
		int thumUriIndex = cur.getColumnIndexOrThrow(Thumbnails.DATA);
		String thumUrl = cur.getString(thumUriIndex);
		Bitmap bitmap = getBitmap((Activity) context, thumUrl);
		return bitmap;
	}

	/**
	 * 获取想要的图片的大小
	 *
	 * @param width
	 *            想要的图片的宽度
	 * @param height
	 *            想要的图片的高度
	 * @return
	 */
	public static Bitmap getBitmap(Activity activity, String imagePath,
								   int width, int height) {
		// 获取手机屏幕大小
		Display currentDisplay = activity.getWindowManager()
				.getDefaultDisplay();
		Point point = new Point();
		currentDisplay.getSize(point);
		int dw = width;
		int dh = height;
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// 加载图片尺寸而不是图像本身
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = null;
		bmp = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);

		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);
		// 如果两个比率都大于1
		// 那么图像的一条边将大于屏幕
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// 若高度比率更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// 若宽度比例更大，则根据它缩放
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// 对他进行真正的解码
		bmpFactoryOptions.inJustDecodeBounds = false;

		bmp = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);
		return bmp;
	}


}
