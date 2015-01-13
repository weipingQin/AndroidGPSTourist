package com.qinweiping.toutistassistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.touristassistant.R;

public class WelcomeActivity extends Activity
{
	
   private void oncreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.welcome);
	   Start();
	   Intent intent = new Intent();
	   intent.setClass(WelcomeActivity.this,MainActivity.class);
	   startActivity(intent);
   }
   
   public void Start()
   {
	   new Thread()
	   {
		   public void run()
		   {
			   try
			   {
				   Thread.sleep(1500);
			   }catch(InterruptedException  e)
			   {
				   e.printStackTrace();
			   }
			   Intent intent = new Intent();
			   intent.setClass(WelcomeActivity.this,MainActivity.class);
			   startActivity(intent);
			   //finish();
		   }
	   }.start();
   }
}
