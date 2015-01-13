package com.qinweiping.toutistassistant.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.touristassistant.R;

public class CurrentLocation extends Activity {

	private MapView mapView;
	private MapController mapController;

	LocationListener mLocationListener = null;
	
	MyLocationOverlay mLocationOverlay = null;//��λͼ��
	
	private double dLat;
	
	private double dLon;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mapView = (MapView)findViewById(R.id.bmapView);
		mapController = mapView.getController();
		//����һ��GeoPoint 
		//�ŵ�mapController.animateTo(GeoPoint);
		
		mapController.animateTo(null);
	}
	
	//��λ��ת����GeoPoint
	private GeoPoint getGeoPointByLocation(Location location)
	{
		GeoPoint gp = null;
		try
		{
			if(location!=null)
			{
				double geoLatitude=location.getLatitude()*1E6;
				double geoLongitude = location.getLongitude()*1E6;
				gp = new GeoPoint((int)geoLatitude,(int)geoLongitude);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return gp;
	}
	
	//��λ���Լ���λ�� 
	public void myself()
	{
		LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location == null)
		{
			Toast.makeText(CurrentLocation.this,"�޷���ȡ��ǰλ��",Toast.LENGTH_SHORT).show();
			//Ĭ��λ�� 
			   dLat = 34.824289;  
	           dLon = 113.689044;   
	           GeoPoint geoPoint = new GeoPoint((int)(dLat*1e6),(int)(dLon*1e6));
	           mapController.setCenter(geoPoint);
		}else
		{
			dLat = (int)(location.getLatitude()*1E6);
			dLon = (int)(location.getLongitude()*1E6);
		}
	}
	
	//������Ļ��� 
	class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>
	{
		public MyItemizedOverlay(Drawable arg0, MapView arg1) {
			super(arg0, arg1);
			// TODO Auto-generated constructor stub
		}

		//���� 
		private Drawable maker;
		
		private Context context;
		
		private List<OverlayItem> geoList = new ArrayList<OverlayItem>();
		
		
/*
		public MyItemizedOverlay(Drawable maker, Context context) {
			super(maker, context);*/
			
		}
		
	}

