package com.qinweiping.toutistassistant.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.touristassistant.R;

public class TakePhotosActivity extends Activity {
	public static final String TAG="test";
	
	private Button BtnTakePhoto ;
	
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.takephoto);
		Intent intent = getIntent();
		BtnTakePhoto = (Button)findViewById(R.id.TakephotoBtn);
		BtnTakePhoto.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent,1);
			}
			
		});
	}
	

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode,resultCode,data);
	
		String sdStatus = Environment.getExternalStorageState();
		
		if(!sdStatus.equals(Environment.MEDIA_MOUNTED))//检测SD卡是否可用
		{
			Log.d(TAG,"SD card is not avaiable/writeable right now.");
			return;
		}
		
		  Bundle bundle = data.getExtras();  
	        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  
	        File file = new File("/sdcard/myImage/");  
	        FileOutputStream bout = null;  
	        file.mkdirs();// 创建文件夹  
	        String fileName = "/sdcard/myImage/111.jpg";  
	        try {  
	            bout = new FileOutputStream(fileName);  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bout);// 把数据写入文件  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                bout.flush();  
	                bout.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
	}
	
}
