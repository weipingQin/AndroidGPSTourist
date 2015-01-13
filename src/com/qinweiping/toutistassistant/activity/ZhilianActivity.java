package com.qinweiping.toutistassistant.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.touristassistant.R;
import com.shoushuo.android.tts.ITts;
import com.shoushuo.android.tts.ITtsCallback;

public class ZhilianActivity extends Activity{

public static final String TAG="test";
	
	private Context context;

	private Button btnBack;
	// 加载相关的语音组件
	private Button btnSound;
	// 加载文字介绍控件
	private TextView showIntro;
	
	//语音朗读相关组件
	private ITts ttsService;
	private boolean ttsBound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhilianshowfeatureitemdetail);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnSound = (Button)findViewById(R.id.btnSound);
		showIntro =(TextView)findViewById(R.id.showfeatureIntro);
		Intent intent = getIntent();
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ZhilianActivity.this.finish();
			}
		});
	}
	
	
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
