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
	 * baiduMap��ص�һЩ��
	 */
	// MapView
	public MapView mMapView = null;
	
	//������ͼ������ 
	public MapController mc = null;

	public LocationClient mLocationClent = null;
	// ��������ָ���������
	public BDLocationListener myListener = new MyLocationListener();

	// ��ͼ������
	private MapController mMapController = null;

	// ��ͼ��ʾ�¼�������
	private MKMapViewListener mMapListener = null;

	// ����ģ��
	// private MKSearch mSearch = null;

	public static String mStrSuggestions[] = {};
	// ��Ļ�ĸ߶�
	private static float screenHeight;
	// ��Ļ�Ŀ��
	private static float screenWidth;

	// Android��λ���
	private LocationManager lm;// λ�ù��������
	private LocationListener mLocationListener;// λ�ñ仯������

	// BaiduMap��λ���
	LocationData locData = null;

	// ��λ���
	LocationClient mLocClient;
	// ��ͼ������
	MyLocationOverlay myLocationOverlay = null;

	// ��ǰ��γ��
	private GeoPoint myPoint;

	// ��������ͼ��
	private PopupOverlay   pop  = null;//��������ͼ�㣬����ڵ�ʱʹ��
	private TextView  popupText = null;//����view
	private View viewCache = null;
	
	
	SharedPreferences sp_filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		// ���ú���
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;// �����Ļ�߶�
		screenWidth = dm.widthPixels;// �����Ļ���
		// ��ȡsharedPreferences�л�ȡ��־ֵ���ж��Ƿ��Ǳ��������е�һ�δ�������

		SharedPreferences sp_isOpen = this.getSharedPreferences("isOpen",
				Context.MODE_PRIVATE);// ֻ�б�����ɷ���ģʽ��
		boolean isOpen = sp_isOpen.getBoolean("isOpen", true);// ���޴洢ֵ��Ĭ����true���ǵ�һ�δ�
		sp_filePath = getSharedPreferences("filePath", Context.MODE_PRIVATE);
		String serviceName = Context.LOCATION_SERVICE;// ��Ҫ�ķ�������
		lm = (LocationManager) this.getSystemService(serviceName);// ��ȡλ�ù�����ʵ��
		// ��ʾ��GPS
		if (isOpen
				&& lm.isProviderEnabled(LocationManager.GPS_PROVIDER) != true)// �жϵ�ǰGPS�Ƿ��
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

		// ���������������ſؼ�
		mMapView.setBuiltInZoomControls(true);
		// ��������ʱ�ػ渲����
		mMapController.enableClick(true);
		//����һ��GeoPoint �����趨��ǰ��λ��  ע�⾭γ��Ҫ�ߵ�д ���ǵ�ͼ��bug
		GeoPoint currentPoint = new GeoPoint((int)(29.991971*1000000),(int)(120.576756*1000000));
		// ���õ�ͼ���ĵ�
	    mMapController.animateTo(currentPoint);
		// ���õ�ͼ���ű���
		mMapController.setZoom(15);
		
		LocationItemsOverlay locationOverlay = new LocationItemsOverlay(getResources().getDrawable(R.drawable.icon_gcoding), mMapView, this);
		
		//���ظ������еľ�����Ϣ 
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

					double Longitude = location.getLongitude();// ��ȡ����
					double Latitude = location.getLatitude();// ��ȡγ��
					myPoint = new GeoPoint((int) (Longitude * 1E6),
							(int) (Latitude * 1E6));

					mMapController.animateTo(myPoint);
					// ���õ�ͼ���ĵ�
					mMapController.setCenter(myPoint);
					// �趨������
					mMapController.setZoom(16);
				}
			}
		};

		// ��ȡ��ǰ���㰴ť������
		LinearLayout BtnCurrent = (LinearLayout) findViewById(R.id.curbutton);
		BtnCurrent.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				whereAmI();
			}
			
			LocationItemsOverlay currentItemOverlay = 
					new LocationItemsOverlay(getResources().getDrawable(R.drawable.ballon),mMapView);

			//��ǰ����ĳλ�õĺ��� 
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

		// ��ȡ���о��㰴ť������
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

		// ��ȡ���������ť������
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

		// ��ȡ���հ�ť������
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
		

		// ��ȡ���ఴť������
		LinearLayout BtnMore = (LinearLayout) findViewById(R.id.more);
		BtnMore.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "btn	BtnMore is Click ");
			}
		});

		// ��ȡ�˳���ť������
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
			// ��ȡγ��ֵ
			locData.latitude = location.getLatitude();
			// ��ȡ����ֵ
			locData.longitude = location.getLongitude();

			// �������ʾ��λ����Ȧ����accuracy��ֵΪ0����
			locData.accuracy = location.getRadius();
			// �˴��������� locData�ķ�����Ϣ, �����λ SDK δ���ط�����Ϣ���û������Լ�ʵ�����̹�����ӷ�����Ϣ��
			locData.direction = location.getDerect();

			// ���¶�λ����
			myLocationOverlay.setData(locData);
			// ����ͼ������ִ��ˢ�º���Ч
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
		// �˳�ʱ���ٶ�λ
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
