package com.geo.com.geo.power.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

/**
 * 根据Bitmap生产各种特效
 *
 * @author Administrator
 *
 */
public class BitmapDuang {
	/**
	 * 原图片去色
	 *
	 * @param pixels
	 * @param width
	 * @param height
	 * @return
	 */
	private static int[] getGray(int[] pixels, int width, int height) {
		int gray[] = new int[width * height];
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height - 1; j++) {
				int index = width * j + i;
				int rgba = pixels[index];
				int g = ((rgba & 0x00FF0000) >> 16) * 3
						+ ((rgba & 0x0000FF00) >> 8) * 6
						+ ((rgba & 0x000000FF)) * 1;
				gray[index] = g / 10;
			}
		}

		return gray;
	}

	/**
	 * 对去色灰度图取反色
	 *
	 * @param gray
	 * @return
	 */
	private static int[] getInverse(int[] gray) {
		int[] inverse = new int[gray.length];

		for (int i = 0, size = gray.length; i < size; i++) {
			inverse[i] = 255 - gray[i];
		}
		return inverse;
	}

	/**
	 * 对反色高斯模糊
	 *
	 * @param inverse
	 * @param width
	 * @param height
	 * @return
	 */
	private static int[] guassBlur(int[] inverse, int width, int height) {
		int[] guassBlur = new int[inverse.length];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int temp = width * (j) + (i);
				if ((i == 0) || (i == width - 1) || (j == 0)
						|| (j == height - 1)) {
					guassBlur[temp] = 0;
				} else {
					int i0 = width * (j - 1) + (i - 1);
					int i1 = width * (j - 1) + (i);
					int i2 = width * (j - 1) + (i + 1);
					int i3 = width * (j) + (i - 1);
					int i4 = width * (j) + (i);
					int i5 = width * (j) + (i + 1);
					int i6 = width * (j + 1) + (i - 1);
					int i7 = width * (j + 1) + (i);
					int i8 = width * (j + 1) + (i + 1);
					int sum = inverse[i0] + 2 * inverse[i1] + inverse[i2] + 2
							* inverse[i3] + 4 * inverse[i4] + 2 * inverse[i5]
							+ inverse[i6] + 2 * inverse[i7] + inverse[i8];

					sum /= 16;

					guassBlur[temp] = sum;
				}
			}
		}
		return guassBlur;
	}

	/**
	 * 对取得高斯灰度值与 去色灰度值 进行颜色减淡混合
	 *
	 * @param guassBlur
	 * @param gray
	 * @param width
	 * @param height
	 * @return
	 */
	private static int[] deceasecolorCompound(int[] guassBlur, int[] gray,
											  int width, int height) {

		int a, b, temp;
		float ex;
		int[] output = new int[guassBlur.length];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int index = j * width + i;
				b = guassBlur[index];
				a = gray[index];

				temp = a + a * b / (256 - b);
				ex = temp * temp * 1.0f / 255 / 255;
				temp = (int) (temp * ex);

				a = Math.min(temp, 255);

				output[index] = a;
			}
		}
		return output;
	}

	/**
	 * 根据混合结果灰度值生产图片
	 *
	 * @param pixels
	 * @param output
	 * @param width
	 * @param height
	 * @return
	 */
	private static Bitmap create(int[] pixels, int[] output, int width,
								 int height) {
		for (int i = 0, size = pixels.length; i < size; i++) {
			int gray = output[i];
			int pixel = (pixels[i] & 0xff000000) | (gray << 16) | (gray << 8)
					| gray;// 注意加上原图的 alpha通道

			output[i] = pixel;
		}

		return Bitmap.createBitmap(output, width, height, Config.ARGB_8888);
	}

	/**
	 * 最后，是应用程序直接调用此方法创建素描图像
	 *
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createPencli(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int[] gray = getGray(pixels, width, height);
		int[] inverse = getInverse(gray);

		int[] guassBlur = guassBlur(inverse, width, height);

		int[] output = deceasecolorCompound(guassBlur, gray, width, height);

		return create(pixels, output, width, height);
	}

	/**
	 * 图片怀旧效果
	 */
	public static Bitmap OldRemeberImage(Bitmap bmp,float rR,float rG,float rB,float gR,float gG,float gB,float bR,float bG,float bB ) {
		/*
		 * 怀旧处理算法即设置新的RGB R=0.393r+0.769g+0.189b G=0.349r+0.686g+0.168b
		 * B=0.272r+0.534g+0.131b
		 */
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		// 循环对每一个象素进行赋值新的颜色，根据高和宽来遍历
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				newR = (int) (rR * pixR + rG * pixG + rB * pixB);
				newG = (int) (gR * pixR + gG * pixG + gB * pixB);
				newB = (int) (bR * pixR + bG * pixG + bB * pixB);
				int newColor = Color.argb(255, newR > 255 ? 255 : newR,
						newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
				pixels[width * i + k] = newColor;
			}
		}
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 获取图片的浮雕效果 底片效果也非常简单:将当前像素点的RGB值分别与255之差后的值作为当前点的RGB
	 * 灰度图像:通常使用的方法是gray=0.3*pixR+0.59*pixG+0.11*pixB
	 *
	 * @param bmp
	 * @return
	 */
	public static Bitmap reliefBitmap(Bitmap bmp) {
		/*
		 * 算法原理：(前一个像素点RGB-当前像素点RGB+127)作为当前像素点RGB值 在ABC中计算B点浮雕效果(RGB值在0~255)
		 * B.r = C.r - B.r + 127 B.g = C.g - B.g + 127 B.b = C.b - B.b + 127
		 */
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1; i < height - 1; i++) {
			for (int k = 1; k < width - 1; k++) {
				// 获取前一个像素颜色
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				// 获取当前像素
				pixColor = pixels[(width * i + k) + 1];
				newR = Color.red(pixColor) - pixR + 127;
				newG = Color.green(pixColor) - pixG + 127;
				newB = Color.blue(pixColor) - pixB + 127;
				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));
				pixels[width * i + k] = Color.argb(255, newR, newG, newB);
			}
		}
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 图片柔滑，可以达到美白、去斑等效果
	 *
	 * @param bmp
	 * @return
	 */
	public static Bitmap softBitmap(Bitmap bmp) {
		int color = bmp.getPixel(12, 18);
		return null;
	}

	/**
	 * 冰冻效果滤镜
	 *
	 * @param bmp
	 * @return
	 */
	public static Bitmap iceBitmap(Bitmap bmp) {

		int width = bmp.getWidth();
		int height = bmp.getHeight();

		int dst[] = new int[width * height];
		bmp.getPixels(dst, 0, width, 0, 0, width, height);

		int R, G, B, pixel;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos]; // 获取图片当前点的像素值

				R = Color.red(pixColor); // 获取RGB三原色
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				pixel = R - G - B;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				R = pixel; // 计算后重置R值，以下类同

				pixel = G - B - R;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = B - R - G;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				dst[pos] = Color.rgb(R, G, B); // 重置当前点的像素值
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 连环画
	 *
	 * @param bmp
	 * @return
	 */
	public static Bitmap manmanPicture(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int R, G, B, pixel;
		int pos, pixColor;
		int dst[] = new int[width * height];
		for (int y = 1; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos]; // 获取图片当前点的像素值

				R = Color.red(pixColor); // 获取RGB三原色
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				// R = |g – b + g + r| * r / 256;
				pixel = G - B + G + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				// G = |b – g + b + r| * r / 256;
				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				// B = |b – g + b + r| * g / 256;
				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * G / 256;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				dst[pos] = Color.rgb(R, G, B); // 重置当前点的像素值
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return bitmap;
	}
}
