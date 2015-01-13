package com.qinweiping.toutistassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.touristassistant.R;
import com.qinweiping.toutistassistant.utils.BMapUtil;
import com.qinweiping.toutistassistant.utils.DemoApplication;
import com.qinweiping.toutistassistant.utils.LocationItemsOverlay;

public class Test extends Activity implements LocationListener{

	public static final String TAG="Test";
	//加载MapView对象 
	MapView mapView=null;
	//MapController 地图控制器 
	MapController mc=null;
	//用于地图事件回调
	MKMapViewListener mMapListener = null;
	//LocationListener locListener;

	//用来加载覆盖物的图标
	Drawable marker  = null;
	
	//加入定位api相关内容 
	LocationListener locListener = null;
	//创建 LocationManager
	LocationManager locationmanager = null;
	//添加气泡弹出覆盖物 
	private PopupOverlay pop = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		/**
         * 使用地图sdk前需先初始化BMapManager.
         * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
		
        DemoApplication app = (DemoApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            app.mBMapManager.init(new DemoApplication.MyGeneralListener());
        }
		
		setContentView(R.layout.test);
		mapView = (MapView)findViewById(R.id.bmapsView);
		//获取地图控制器
		MapController mc = mapView.getController();
		
		 /**
         *  设置地图是否响应点击事件  .
         */
		mc.enableClick(true);
        
	    mapView.setBuiltInZoomControls(true);
	    /**
         * 设置地图缩放级别
         */
	    mc.setZoom(12);
	
		//此GeoPoint 用来加载当前的Location 
		//GeoPoint point = new GeoPoint((int)(39.94214*1000000),(int)(19.407413*1000000));
	    //mc.setCenter(point);
		
		//下面一组OverlayItem 用来加载一个集合 是用来做周边的所有景点来用的 
	    Log.d(TAG,"debug1");
	    
	    LocationItemsOverlay locationOverlay = new LocationItemsOverlay
				(getResources().getDrawable(R.drawable.icon_gcoding), mapView,this);
	    
	    //定义一个当前位置的覆盖物 用来显示当前位置 
	    LocationItemsOverlay currentLocationOverlay = new LocationItemsOverlay
	    		(getResources().getDrawable(R.drawable.icon_geo),mapView,this);
		
		OverlayItem item1 = new OverlayItem(locationOverlay.getP1(),"item1","item1");
		  Log.d(TAG,"debugitem1");
		OverlayItem item2 = new OverlayItem(locationOverlay.getP2(),"item2","item2");
		  Log.d(TAG,"debug2");
		OverlayItem item3 = new OverlayItem(locationOverlay.getP3(),"item3","item3");
		  Log.d(TAG,"debug3");
		
		/*locationOverlay = new LocationItemsOverlay
				(getResources().getDrawable(R.drawable.icon_geo), mapView);*/
		  Log.d(TAG,"locationOverlay");
		mapView.getOverlays().clear();
		 Log.d(TAG,"mapView");
		mapView.getOverlays().add(locationOverlay);
		//现在所有准备工作已准备好，使用以下方法管理overlay.  
		//添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高  
		locationOverlay.addItem(item1);
		 Log.d(TAG,"geoPoint1>>>"+locationOverlay.getP1());
		locationOverlay.addItem(item2);
		Log.d(TAG,"geoPoint2>>>"+locationOverlay.getP2());
		locationOverlay.addItem(item3);
		Log.d(TAG,"geoPoint3>>>"+locationOverlay.getP3());
		mapView.refresh();
		Log.d(TAG,"refresh....");
		
		
		 /*  
		  * 此处需要添加地图响应的一些事件
		  * mMapListener = new MKMapViewListener() 
		   {

			@Override
			public void onClickMapPoi(MapPoi arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetCurrentMap(Bitmap arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapAnimationFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapLoadFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMapMoveFinish() {
				// TODO Auto-generated method stub
				
			}
			   
		   };*/
		
		mapView.regMapViewListener(DemoApplication.getInstance().mBMapManager, mMapListener);
		Log.d(TAG,"mapView regMapListener");
		
	}
	
	protected boolean isRouteDisplayed()
	{
		return false;
	}
	
	//onPause中 关闭GPS服务
	@Override
    protected void onPause() {
		mapView.onPause();
	        super.onPause();
	        if(locationmanager!=null)
	        {
	        	locationmanager.removeUpdates(locListener);
	        }
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    	 */
    	mapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
    	 */
    	mapView.destroy();
        super.onDestroy();
    }

    //当位置改变时触发的事件
	@Override
	public void onLocationChanged(Location location) {
		//得到当前的纬度值
		double latitude = location.getLatitude();
		Log.d(TAG,"the current latitude>>>"+latitude);
		//得到当前的经度值
		double longitude = location.getLongitude();
		Log.d(TAG,"the current longitude>>>"+longitude);
		GeoPoint currentGP = new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
		//将当前经纬度坐标放入OverlayItem
		OverlayItem currentOverlayItem = new OverlayItem(currentGP,"item1","item1");
		LocationItemsOverlay lo = new LocationItemsOverlay(marker, mapView, this);
		lo.addItem(currentOverlayItem);
		Log.d(TAG,"debug currentLocation");
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
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    
 /*   @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mapView.onRestoreInstanceState(savedInstanceState);
    }*/
    
	//弹出覆盖物 
	public void showPopupOverlay(GeoPoint p1)
	{
		//创建pop对象 注册点击事件监听接口 
		pop = new PopupOverlay(mapView,new PopupClickListener()
		{

			@Override
			public void onClickedPopup(int index) {
				//在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个
			}
			
		});
		
		ViewGroup root;
		View view = getLayoutInflater().inflate(R.layout.pop_layout, null);
		View pop_layout =view.findViewById(R.id.pop_layout);
		
		//将view转化成显示的bitmap
		Bitmap[] bitMaps = {BMapUtil.getBitmapFromView(pop_layout)};
		pop.showPopup(bitMaps,p1,32);
	}   
	
	//将经纬度转化成GeoPoint
	private GeoPoint transformToGeoPoint(double lati,double lng)
	{
		return new GeoPoint((int)(lati*1E6),(int)(lng*1E6));
	}
}

