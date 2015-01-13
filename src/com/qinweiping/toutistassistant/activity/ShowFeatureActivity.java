package com.qinweiping.toutistassistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.touristassistant.R;

public class ShowFeatureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showfeature);
		Intent intent = getIntent();
	}
}
