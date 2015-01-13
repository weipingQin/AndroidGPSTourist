package com.qinweiping.toutistassistant.utils;

import java.util.List;
import java.util.Map;

import com.example.touristassistant.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String,Object>> listItems;
	private LayoutInflater listContainer;
	
	
	public final class ListItemView
	{
		public ImageView image;
		public TextView text;
	}
	
	public  MyAdapter(Context context,List<Map<String , Object>>listItems,int length) {//初始化信息
		// TODO Auto-generated constructor stub
    	this.context=context;
    	listContainer=LayoutInflater.from(context);
    	this.listItems=listItems;
	}
	
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		System.out.println("getItem");
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		System.out.println("getItemId");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if(convertView ==null)
		{
			listItemView = new ListItemView();//获得封装的对象 
			convertView = listContainer.inflate(R.layout.featureitem, null);
			listItemView.image = (ImageView)convertView.findViewById(R.id.image);
			listItemView.text =(TextView)convertView.findViewById(R.id.title);
			convertView.setTag(listItemView);//设置控件集到convertView
		}
		else
		{
			listItemView=(ListItemView)convertView.getTag();
		}
		//此处填入的参数是drawable
		listItemView.image.setImageResource((Integer)listItems.get(position).get("image"));//??
		listItemView.text.setText((String)listItems.get(position).get("id"));
		
		return convertView;
		
	}


}
