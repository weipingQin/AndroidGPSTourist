package com.qinweiping.toutistassistant.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.touristassistant.R;
import com.qinweiping.toutistassistant.utils.DemoApplication;
import com.qinweiping.toutistassistant.utils.LocationItemsOverlay;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";

	/*
	 * baiduMap相关的一些类
	 */
	// MapView
	public MapView mMapView = null;
	
	//声明地图控制器 
	public MapController mc = null;

	public LocationClient mLocationClent = null;
	// 父类引用指向子类对象
	public BDLocationListener myListener = new MyLocationListener();

	// 地图控制器
	private MapController mMapController = null;

	// 地图显示事件监听器
	private MKMapViewListener mMapListener = null;

	// 搜索模块
	// private MKSearch mSearch = null;

	public static String mStrSuggestions[] = {};
	// 屏幕的高度
	private static float screenHeight;
	// 屏幕的宽度
	private static float screenWidth;

	// Android定位相关
	private LocationManager lm;// 位置管理监听器
	private LocationListener mLocationListener;// 位置变化监听器

	// BaiduMap定位相关
	LocationData locData = null;

	// 定位相关
	LocationClient mLocClient;
	// 地图覆盖物
	MyLocationOverlay myLocationOverlay = null;

	// 当前经纬度
	private GeoPoint myPoint;

	// 弹出泡泡图层
	private PopupOverlay   pop  = null;//弹出泡泡图层，浏览节点时使用
	private TextView  popupText = null;//泡泡view
	private View viewCache = null;
	
	
	SharedPreferences sp_filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		// 设置横屏
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;// 获得屏幕高度
		screenWidth = dm.widthPixels;// 获得屏幕宽度
		// 获取sharedPreferences中获取标志值，判断是否是本次运行中第一次打开主界面

		SharedPreferences sp_isOpen = this.getSharedPreferences("isOpen",
				Context.MODE_PRIVATE);// 只有本程序可访问模式打开
		boolean isOpen = sp_isOpen.getBoolean("isOpen", true);// 若无存储值，默认是true，是第一次打开
		sp_filePath = getSharedPreferences("filePath", Context.MODE_PRIVATE);
		String serviceName = Context.LOCATION_SERVICE;// 需要的服务名称
		lm = (LocationManager) this.getSystemService(serviceName);// 获取位置管理器实例
		// 提示打开GPS
		if (isOpen
				&& lm.isProviderEnabled(LocationManager.GPS_PROVIDER) != true)// 判断当前GPS是否打开
		{
			Log.d(TAG, "GPS is open>>>" + isOpen);
			Intent callGPSSettingIntent = new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(callGPSSettingIntent);
			Toast.makeText(this, getResources().getString(R.string.opengps),
					Toast.LENGTH_LONG).show();
			SharedPreferences.Editor editor = sp_isOpen.edit();
			editor.putBoolean("isOpen", false);
			editor.commit();
		}

		DemoApplication app = (DemoApplication) this.getApplication();

		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(getApplication());

			app.mBMapManager.init(new DemoApplication.MyGeneralListener());
		}

	
		setContentView(R.layout.main);
		
		if (mMapView == null) {
			mMapView = (MapView) findViewById(R.id.bmapView);

		}
		
		mMapController = mMapView.getController();

		// 设置启动内置缩放控件
		mMapView.setBuiltInZoomControls(true);
		// 设置缩放时重绘覆盖物
		mMapController.enableClick(true);
		//构造一个GeoPoint 用来设定当前的位置  注意经纬度要颠倒写 这是地图的bug
		GeoPoint currentPoint = new GeoPoint((int)(29.991971*1000000),(int)(120.576756*1000000));
		// 设置地图中心点
	    mMapController.animateTo(currentPoint);
		// 设置地图缩放比例
		mMapController.setZoom(15);
		
		LocationItemsOverlay locationOverlay = new LocationItemsOverlay(getResources().getDrawable(R.drawable.icon_gcoding), mMapView, this);
		
		//加载附近所有的景点信息 
		OverlayItem item1 = new OverlayItem(locationOverlay.getP1(),"item1","item1");
		  Log.d(TAG,"debugitem1");
		OverlayItem item2 = new OverlayItem(locationOverlay.getP2(),"item2","item2");
		  Log.d(TAG,"debug2");
		OverlayItem item3 = new OverlayItem(locationOverlay.getP3(),"item3","item3");
		  Log.d(TAG,"debug3");
		
		  mMapView.getOverlays().clear();
		  
		  mMapView.getOverlays().add(locationOverlay);
		  
		  locationOverlay.addItem(item1);
		  locationOverlay.addItem(item2);
		  locationOverlay.addItem(item3);
		  
		  mMapView.refresh();

		 mLocationListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLocationChanged(Location location) {

				if (null != location) {

					double Longitude = location.getLongitude();// 获取经度
					double Latitude = location.getLatitude();// 获取纬度
					myPoint = new GeoPoint((int) (Longitude * 1E6),
							(int) (Latitude * 1E6));

					mMapController.animateTo(myPoint);
					// 设置地图中心点
					mMapController.setCenter(myPoint);
					// 设定比例尺
					mMapController.setZoom(16);
				}
			}
		};

		// 获取当前景点按钮的引用
		LinearLayout BtnCurrent = (LinearLayout) findViewById(R.id.curbutton);
		BtnCurrent.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				whereAmI();
			}
			
			LocationItemsOverlay currentItemOverlay = 
					new LocationItemsOverlay(getResources().getDrawable(R.drawable.ballon),mMapView);

			//当前处于某位置的函数 
			protected void whereAmI()
			{
				
				lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				
				  LocationListener mLocationListener = new LocationListener()
				{

					@Override
					public void onLocationChanged(Location location) {
						if(null !=location)
						{
						double lat = location.getLatitude();
						double lng = location.getLongitude();
						
						GeoPoint currentGp = new GeoPoint((int)(lat*1E6),(int)(lng*1E6));
						OverlayItem currentitem = new OverlayItem(currentGp,"currentItem","currentItem");
						mMapView.getOverlays().clear();
						mMapView.getOverlays().add(currentItemOverlay);
						currentItemOverlay.addItem(currentitem);
						mMapView.refresh();
						//mc.animateTo(currentGp);
						}
						
					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub
						
					}
					
				};
				
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,mLocationListener);
				
			}
		});

		// 获取所有景点按钮的引用
		LinearLayout BtnAll = (LinearLayout) findViewById(R.id.allbutton);
		BtnAll.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Intent intent = new Intent();
				Log.d(TAG, "btnAll is Click");
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,ShowAllFeaturesActivity.class);
				startActivity(intent);
			}
		});

		// 获取景点概览按钮的引用
		LinearLayout BtnLock = (LinearLayout) findViewById(R.id.suoding);
		BtnLock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "btnLock is Click ");
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,SenicActivity.class);
				startActivity(intent);
			}

		});

		// 获取拍照按钮的引用
		LinearLayout BtnCamera = (LinearLayout) findViewById(R.id.camera);
		BtnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "btn	BtnCamera is Click ");
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,TakePhotosActivity.class);
				startActivity(intent);
			}

		});
		

		// 获取更多按钮的引用
		LinearLayout BtnMore = (LinearLayout) findViewById(R.id.more);
		BtnMore.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "btn	BtnMore is Click ");
			}
		});

		// 获取退出按钮的引用
		LinearLayout BtnExit = (LinearLayout) findViewById(R.id.exit);
		BtnExit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "btn	BtnExit is Click ");
				MainActivity.this.finish();
			}
		});
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			// 获取纬度值
			locData.latitude = location.getLatitude();
			// 获取经度值
			locData.longitude = location.getLongitude();

			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();

			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();

		}
		
		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			if (poiLocation == null) {
				return;
			}
		}

	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}
}
