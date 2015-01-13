package com.qinweiping.toutistassistant.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public  class LocationItemsOverlay extends ItemizedOverlay<OverlayItem> implements LocationListener {

	public static final String TAG ="test";
	private List<OverlayItem> items = null;
	private Drawable marker = null;
	private MapView mapView = null;
	private Context context = null;
	private MapController mc = null;
	private LocationListener ll = null;
	private Activity activity = null;
	
	public LocationItemsOverlay(Drawable marker, MapView mapView,Context context) {
		super(marker, mapView);
		this.marker = marker;
		//将mapView初始化
		this.mapView = mapView;
		this.context = context;
	}
	
	public LocationItemsOverlay(Drawable marker,MapView mapView)
	{
		super(marker,mapView);
		this.marker = marker;
		this.mapView = mapView;
	}
	
	protected OverlayItem createItem(int i )
	{
		return items.get(i);
	}
	
	protected boolean onTap(int index)
	{
		System.out.println("item onTap:"+index);
		
		//弹出覆盖物 
		//showPopupOverlay(getItem(index).getPoint());
		return true;
	}
	

	public boolean onTap(GeoPoint gp, MapView mapView)
	{
		super.onTap(gp, mapView);
		
		return false;
	}
	//百度地图经纬度坐标采集 网址:http://api.map.baidu.com/lbsapi/getpoint/index.html
	//河西餐厅经纬度坐标点 
	public double mLat1 = 29.994942;  
	public double mLon1 = 120.577037;  
	//绍兴文理学院图书馆 
	public double mLat2 = 29.991935;  
	public double mLon2 = 120.577371; 
	//绍兴文理学院理工楼
	public double mLat3 = 29.992604;  
	public double mLon3 = 120.577692;  
	//绍兴文理学院铁成科技馆
	public double mLat4 = 29.992283;
	public double mLon4 = 120.579235;


	
	// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)  
	private GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));  
	private GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));  
	private GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
	
	public GeoPoint getP1() {
		return p1;
	}

	public GeoPoint getP2() {
		return p2;
	}

	public GeoPoint getP3() {
		return p3;
	}
	
	//获取当前位置的GeoPoint
	public GeoPoint curGeoPoint(Location location)
	{
		return new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
	}
	
	

    //当改变时候 触发事件
	@Override
	public void onLocationChanged(Location location) {
		double Latitude = location.getLatitude();
		double Longitude = location.getLongitude();
		GeoPoint currentgp = new GeoPoint((int)(Latitude*1E6),(int)(Longitude));
		Log.d(TAG,"current location Latitude"+Latitude+"current location Longitude"+Longitude);
		 mc.animateTo(currentgp);
		 Log.d(TAG,"mc>>>mapController 没有初始化。。。。");
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
}
