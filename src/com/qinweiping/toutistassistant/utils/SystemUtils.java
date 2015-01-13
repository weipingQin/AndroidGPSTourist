package com.qinweiping.toutistassistant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Window;

public class SystemUtils {
	
	//Ҫʹ�õ���ģʽ 
	

	private Context context = null;
	
	private static Activity activity = null;
	
	// ��Ļ�ĸ߶�
	private static float screenHeight;
	// ��Ļ�Ŀ��
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
		// ���ú���
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;// �����Ļ�߶�
		screenWidth = dm.widthPixels;// �����Ļ���
	}
}
