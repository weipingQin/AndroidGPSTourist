package com.qinweiping.toutistassistant.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

import com.example.touristassistant.R;
import com.qinweiping.toutistassistant.utils.ImageAdapter;
import com.shoushuo.android.tts.ITts;
import com.shoushuo.android.tts.ITtsCallback;

public class SenicActivity extends Activity {

	public static final String TAG="test";
	private Context context;

	// 屏幕的高度
	private static float screenHeight;
	// 屏幕的宽度
	private static float screenWidth;
	// 加载画廊控件
	private Gallery gallery;

	// 加载相关的语音组件
	private Button btnSound;
	// 加载文字介绍控件
	private EditText showIntro;
	// 或得后退按钮
	private Button btnBack;
	private ITts ttsService;
	private boolean ttsBound;

	// 定义Handler
	private final Handler handler = new Handler() {
		public void HandleMessage(Message msg) {
			Toast.makeText(context, "我的话说完了", Toast.LENGTH_SHORT).show();
			btnSound.setEnabled(true);
			Log.d(TAG,"read is running");
		}
	};

	private final ITtsCallback ttsCallback = new ITtsCallback.Stub() {

		//朗读完毕
		@Override
		public void speakCompleted() throws RemoteException {
			handler.sendEmptyMessage(0);
		}
	};

	//TTS服务连接
	private final ServiceConnection ttsConnection = new ServiceConnection() 
	{
		public void onServiceDisconnected(ComponentName arg0) {
			try {
				ttsService.unregisterCallback(ttsCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			ttsService = null;
			ttsBound = false;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			ttsService = ITts.Stub.asInterface(service);
			ttsBound = true;
			try {

				ttsService.initialize();
				ttsService.registerCallback(ttsCallback);
			} catch (RemoteException e) {
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;// 获得屏幕高度
		screenWidth = dm.widthPixels;// 获得屏幕宽度
		context = this;
		setContentView(R.layout.senic);
		Intent intent = getIntent();
		// 加载语音小图标
		btnSound = (Button) findViewById(R.id.btnSound);
		// 获得后退按钮
		Button btnBack = (Button) findViewById(R.id.btnBack);
		// 加载景点用于放置文字的控件
	    showIntro = (EditText) findViewById(R.id.showIntro);
		// 获得后退按钮的引用
		// 加载画廊控件
		gallery = (Gallery) findViewById(R.id.galleryshow);
		// 设置图片资源适配器
		gallery.setAdapter(new ImageAdapter(this));
		// 设置监听器
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(SenicActivity.this, "点击了第" + arg2 + "张图",
						Toast.LENGTH_LONG).show();
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SenicActivity.this.finish();
			}
		});
	}

	// 按钮朗读
	public void speechText(View v) {
		v.setEnabled(false);
		try {
			ttsService.speak(showIntro.getText().toString(),
					TextToSpeech.QUEUE_FLUSH);
		} catch (RemoteException e) {
			 e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		if (ttsBound) {
			ttsBound = false;
			this.unbindService(ttsConnection);
		}
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!ttsBound) {
			String actionName = "com.shoushuo.android.tts.intent.action.InvokeTts";
			Intent intent = new Intent(actionName);
			this.bindService(intent, ttsConnection, Context.BIND_AUTO_CREATE);
		}
	}

}
