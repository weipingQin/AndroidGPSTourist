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
	//����MapView���� 
	MapView mapView=null;
	//MapController ��ͼ������ 
	MapController mc=null;
	//���ڵ�ͼ�¼��ص�
	MKMapViewListener mMapListener = null;
	//LocationListener locListener;

	//�������ظ������ͼ��
	Drawable marker  = null;
	
	//���붨λapi������� 
	LocationListener locListener = null;
	//���� LocationManager
	LocationManager locationmanager = null;
	//������ݵ��������� 
	private PopupOverlay pop = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		/**
         * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager.
         * BMapManager��ȫ�ֵģ���Ϊ���MapView���ã�����Ҫ��ͼģ�鴴��ǰ������
         * ���ڵ�ͼ��ͼģ�����ٺ����٣�ֻҪ���е�ͼģ����ʹ�ã�BMapManager�Ͳ�Ӧ������
         */
		
        DemoApplication app = (DemoApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(getApplicationContext());
            /**
             * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
             */
            app.mBMapManager.init(new DemoApplication.MyGeneralListener());
        }
		
		setContentView(R.layout.test);
		mapView = (MapView)findViewById(R.id.bmapsView);
		//��ȡ��ͼ������
		MapController mc = mapView.getController();
		
		 /**
         *  ���õ�ͼ�Ƿ���Ӧ����¼�  .
         */
		mc.enableClick(true);
        
	    mapView.setBuiltInZoomControls(true);
	    /**
         * ���õ�ͼ���ż���
         */
	    mc.setZoom(12);
	
		//��GeoPoint �������ص�ǰ��Location 
		//GeoPoint point = new GeoPoint((int)(39.94214*1000000),(int)(19.407413*1000000));
	    //mc.setCenter(point);
		
		//����һ��OverlayItem ��������һ������ ���������ܱߵ����о������õ� 
	    Log.d(TAG,"debug1");
	    
	    LocationItemsOverlay locationOverlay = new LocationItemsOverlay
				(getResources().getDrawable(R.drawable.icon_gcoding), mapView,this);
	    
	    //����һ����ǰλ�õĸ����� ������ʾ��ǰλ�� 
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
		//��������׼��������׼���ã�ʹ�����·�������overlay.  
		//���overlay, ���������Overlayʱʹ��addItem(List<OverlayItem>)Ч�ʸ���  
		locationOverlay.addItem(item1);
		 Log.d(TAG,"geoPoint1>>>"+locationOverlay.getP1());
		locationOverlay.addItem(item2);
		Log.d(TAG,"geoPoint2>>>"+locationOverlay.getP2());
		locationOverlay.addItem(item3);
		Log.d(TAG,"geoPoint3>>>"+locationOverlay.getP3());
		mapView.refresh();
		Log.d(TAG,"refresh....");
		
		
		 /*  
		  * �˴���Ҫ��ӵ�ͼ��Ӧ��һЩ�¼�
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
	
	//onPause�� �ر�GPS����
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
    	 *  MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
    	 */
    	mapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.destroy()
    	 */
    	mapView.destroy();
        super.onDestroy();
    }

    //��λ�øı�ʱ�������¼�
	@Override
	public void onLocationChanged(Location location) {
		//�õ���ǰ��γ��ֵ
		double latitude = location.getLatitude();
		Log.d(TAG,"the current latitude>>>"+latitude);
		//�õ���ǰ�ľ���ֵ
		double longitude = location.getLongitude();
		Log.d(TAG,"the current longitude>>>"+longitude);
		GeoPoint currentGP = new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
		//����ǰ��γ���������OverlayItem
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
    
	//���������� 
	public void showPopupOverlay(GeoPoint p1)
	{
		//����pop���� ע�����¼������ӿ� 
		pop = new PopupOverlay(mapView,new PopupClickListener()
		{

			@Override
			public void onClickedPopup(int index) {
				//�ڴ˴���pop����¼���indexΪ�����������,�����������������
			}
			
		});
		
		ViewGroup root;
		View view = getLayoutInflater().inflate(R.layout.pop_layout, null);
		View pop_layout =view.findViewById(R.id.pop_layout);
		
		//��viewת������ʾ��bitmap
		Bitmap[] bitMaps = {BMapUtil.getBitmapFromView(pop_layout)};
		pop.showPopup(bitMaps,p1,32);
	}   
	
	//����γ��ת����GeoPoint
	private GeoPoint transformToGeoPoint(double lati,double lng)
	{
		return new GeoPoint((int)(lati*1E6),(int)(lng*1E6));
	}
}

