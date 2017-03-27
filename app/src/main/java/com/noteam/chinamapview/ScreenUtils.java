package com.noteam.chinamapview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 与屏幕相关的工具类
 */
public class ScreenUtils {

	/**
	 * 获取屏幕宽度，单位为px
	 * 
	 * @param context
	 * @return 返回int类型的屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获取屏幕高度，单位为px
	 * 
	 * @param context
	 * @return 返回int类型的屏幕高度
	 */
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获取屏幕密度，单位为dpi
	 * 
	 * @param context
	 * @return 返回int类型的屏幕密度
	 */
	public static int getScreenDensityDpi(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		return outMetrics.densityDpi;
	}
	
	/**
	 * 获取缩放系数，值为 densityDpi/160
	 * 
	 * @param context
	 * @return 返回float类型的缩放系数
	 */
	public static float getScreenScale(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		return outMetrics.density;
	}
	
	/**
	 * 获取文字缩放系数，同scale
	 * 
	 * @param context
	 * @return 返回float类型的文字缩放系数
	 */
	public static float getScreenFontScale(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		return outMetrics.scaledDensity;
	}
	
	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return 返回int类型的状态栏高度
	 */
	public static int getStatusHeight(Context context) {
		int statusBarHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object obj = clazz.newInstance();
			Field field = clazz.getField("status_bar_height");
			int heightId = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(heightId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return 返回Bitmap类型的当前屏幕截图(包含状态栏)
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return 返回Bitmap类型的当前屏幕截图(不包含状态栏)
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

}
