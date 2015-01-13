package com.qinweiping.toutistassistant.activity;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.touristassistant.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ShowAllFeaturesActivity extends Activity {
	
	public static final String TAG="ShowAllFeaturesActivity";

	private Button BtnAllBack ;
	
	private LinearLayout showFeatureItemDetail;
	
	private Button BtnDetail;
	// ��Ļ�ĸ߶�
	private static float screenHeight;
	// ��Ļ�Ŀ��
    private static float screenWidth;
	
	//��������Դ 
	private int[] images = 
		{R.drawable.zhilianlou,R.drawable.caodi,R.drawable.generlly,R.drawable.langqiao};
	private String[] text={"־��¥ʵ����","�����µ�������Ů��","У԰һ��","���ţ�ˮ������˹!"};
	
	OnItemClickListener item = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showallfeatures);
		//setContentView(R.layout.featureitem);
		Intent intent = getIntent();
	   //��ʾGridView����
		GridView gridview = (GridView)findViewById(R.id.lvshow);
		ArrayList<HashMap<String,Object>> imagelist = new ArrayList<HashMap<String,Object>>();
		//ʹ��HashMap��ͼƬ���ص������� HashMap��String��ӦͼƬ��idֵ 
		for(int i =0;i<4;i++)
		{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("image",images[i]);
			map.put("text", text[i]);
			imagelist.add(map);
		}
		//ʹ��simpleAdapter��װ���ݽ������ 
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,imagelist,
			R.layout.featureitem,new String[]{"image","text"},
			new int[]{R.id.image,R.id.title}
				);
		
		gridview.setAdapter(simpleAdapter);
		gridview.setOnItemClickListener(new ItemClickListener());
		
		   BtnAllBack = (Button)findViewById(R.id.allback);
		    BtnAllBack.setOnClickListener(new OnClickListener()
		    {
				@Override
				public void onClick(View arg0) {
					ShowAllFeaturesActivity.this.finish();
				}
		    	
		    });
	}
	    class ItemClickListener implements OnItemClickListener 
	    {

	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View arg1, int position, long rowid) {

	    		HashMap<String,Object> item = (HashMap<String,Object>)parent.getItemAtPosition(position);
	    		
	    		//��ȡ����Դ������ֵ 
	    		String itemText = (String)item.get("text");
	    		Object object = item.get("image");
	    		Toast.makeText(ShowAllFeaturesActivity.this, itemText, Toast.LENGTH_LONG).show();
	    		
	    		//����ͼƬ������Ӧ����ת
	    		switch(images[position])
	    		{
	    		case R.drawable.zhilianlou:
	    			startActivity(new Intent(ShowAllFeaturesActivity.this,ZhilianActivity.class));
	    		   // ShowAllFeaturesActivity.this.finish();
	    		    break;
	    		case R.drawable.caodi:
	    			startActivity(new Intent(ShowAllFeaturesActivity.this,CaodiActivity.class));
	    			//ShowAllFeaturesActivity.this.finish();
	    			break;
	    		case R.drawable.generlly:
	    			startActivity(new Intent(ShowAllFeaturesActivity.this,GailanActivity.class));
	    			//ShowAllFeaturesActivity.this.finish();
	    			break;
	    		case R.drawable.langqiao:
	    			ShowAllFeaturesActivity.this.startActivity(new Intent(ShowAllFeaturesActivity.this,LangqiaoActivity.class));
	    		}
	    	}

	    }
	}


