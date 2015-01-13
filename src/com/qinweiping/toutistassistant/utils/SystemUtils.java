package com.qinweiping.toutistassistant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Window;

public class SystemUtils {
	
	//要使用单利模式 
	

	private Context context = null;
	
	private static Activity activity = null;
	
	// 屏幕的高度
	private static float screenHeight;
	// 屏幕的宽度
	private static float screenWidth;

	
	
	public SystemUtils(Context context) {
		this.context = context ;
	}
	
	public SystemUtils(Activity activity)
	{
		this.activity = activity;
	}
	
	public SystemUtils(Context context,Activity activity)
	{
		this.activity = activity;
		this.context = context;
	}
	
	public static void setScreen(Context context)
	{
		// 设置横屏
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;// 获得屏幕高度
		screenWidth = dm.widthPixels;// 获得屏幕宽度
	}
}
