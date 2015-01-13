package com.qinweiping.toutistassistant.activity;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.example.touristassistant.R;
import com.qinweiping.toutistassistant.utils.DemoApplication;

public class TestBaiduMap extends Activity {

	public static final String TAG="TestBaiduMap";
	//1 放置一个MapView 对象
	private MapView mapview = null;
    //2 加载一个MapController对象 地图控制器 
	private MapController mapcontroller = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//在加载地图之前 首先要加载地图管理类 
		
        DemoApplication app = (DemoApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            app.mBMapManager.init(new DemoApplication.MyGeneralListener());
        }
        //加载地图对应的layout页面  此处就是刚才建立的test.xml
		setContentView(R.layout.test);
		//加载地图所对应的id的mapView控件
		mapview = (MapView)findViewById(R.id.bmapsView);
	}
}
