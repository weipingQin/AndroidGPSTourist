package com.qinweiping.toutistassistant.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.touristassistant.R;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	
	
	
	//图片资源数组
	private Integer[] imageInteger=
	{
		R.drawable.langqiao,
		R.drawable.generlly,
		R.drawable.zhilianlou,
		R.drawable.caodi
	};
	
	public ImageAdapter(Context c)
	{
		context = c;
	}
	
	@Override
	public int getCount() {
		return imageInteger.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(imageInteger[position]);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(350, 200));
		return imageView;
	}

}
