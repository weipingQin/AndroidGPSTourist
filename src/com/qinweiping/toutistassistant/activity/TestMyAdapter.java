package com.qinweiping.toutistassistant.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.touristassistant.R;
import com.qinweiping.toutistassistant.utils.MyAdapter;

public class TestMyAdapter extends Activity {
   private MyAdapter myAdapter;
   private GridView gridView;
   
   private String[] text={"aaa","bbb","ccc","ddd"};
   
   private Integer[] images=
	   {R.drawable.generlly,R.drawable.caodi,R.drawable.zhilianlou,R.drawable.langqiao};
   
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showallfeatures);
		
		List<Map<String,Object>> listmap = new ArrayList<Map<String,Object>>();
		for(int i = 0 ;i<text.length;i++)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("images",images[i]);
			map.put("text",text[i]);
			listmap.add(map);
		System.out.println("iamge is add>>>"+images[i]);
		//或得gridView的引用 
		gridView =(GridView)findViewById(R.id.lvshow);
		gridView.setNumColumns(3);
		gridView.setAdapter(myAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				
			}
		});
		}
	}
}
