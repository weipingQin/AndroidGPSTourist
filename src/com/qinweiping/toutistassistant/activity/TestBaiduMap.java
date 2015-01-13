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
	//1 ����һ��MapView ����
	private MapView mapview = null;
    //2 ����һ��MapController���� ��ͼ������ 
	private MapController mapcontroller = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�ڼ��ص�ͼ֮ǰ ����Ҫ���ص�ͼ������ 
		
        DemoApplication app = (DemoApplication)this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(getApplicationContext());
            /**
             * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
             */
            app.mBMapManager.init(new DemoApplication.MyGeneralListener());
        }
        //���ص�ͼ��Ӧ��layoutҳ��  �˴����ǸղŽ�����test.xml
		setContentView(R.layout.test);
		//���ص�ͼ����Ӧ��id��mapView�ؼ�
		mapview = (MapView)findViewById(R.id.bmapsView);
	}
}
