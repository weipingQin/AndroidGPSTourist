package com.qinweiping.toutistassistant.storage;

import com.example.touristassistant.R;

import android.location.Location;
import android.media.MediaPlayer;

public class Constant {
	public static int isPlay=0;//0代表当前无景点介绍，1 代表正在播放�? 代表暂停
	public static String curScenicId=null;//记录当前显示景点的编�?
	public static String curSMP=null;//记录当前正在播报的景点名
	public static Location myLocation;//游客当前的经纬度位置
	public static MediaPlayer mp;//景点介绍使用的播放器
	public static final int PHOTOHRAPH = 1;// 拍照，调用系统照相机时使�?
	public static final String[] LANGUAGE={"�?��中文","English"};//可�?语言种类
	public static final int[] MODE={R.string.modeDao,R.string.modeSelf};//设置模式
	public static final int[] MORE={R.string.share,R.string.findrange,R.string.setting,R.string.selectLan,R.string.userbackBt,R.string.changezip,R.string.about};//更多选择中的选项
	public static final int[] MOREImage={R.drawable.share,R.drawable.search,R.drawable.settings,R.drawable.language,R.drawable.email,R.drawable.changezip,R.drawable.about};//更多选择中的选项的图�?
	public static final int SHOWMOREDIALOG=1;//显示更多对话�?
	public static final int EXIT_DIALOG=2;//询问是否�?��对话�?
	public static final String[] FILE={"scenic.txt","scenic_english.txt"};//景点文件,分别对应�?��中文，繁体中文，英文
	public static final int DISTANCE_USER=100;//用户移动范围的阈�?
	public static final int DISTANCE_SCENIC=200;//景点范围的阈�?
	public static final double EARTH_RADIUS = 6378137.0; //地球半径
	
	public static final String CONSUMER_KEY ="3137261270";// 鏇挎崲涓哄紑鍙戣�鐨刟ppkey锛屼緥濡�?646212960";
	public static final String CONSUMER_SECRET ="d97ff3893abb20f96231fcb7f5d35b43";
	
	public static final int[] TEXT_SIZES={12,18,24,30,36};
	
	//搜索周边兴趣点的范围,显示时以公里为单�?
		public static final int RANGE_UNION=1000;//计算是乘以数组中的数换算成米
		public static final int[] RANGE_ITEMS={1,2,5,10};
	
	//通过两点经纬度计算距离的方法，返回单位为：米
	public static double jWD2M(double lat_a, double lng_a, double lat_b, double lng_b){
		double radLat1 = (lat_a*Math.PI/180.0); 
		double radLat2 = (lat_b*Math.PI/180.0); 
		double a = radLat1-radLat2; 
		double b = (lng_a- lng_b)* Math.PI/180.0; 
		double s = 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) 
        + Math.cos(radLat1)*Math.cos(radLat2)
        * Math.pow(Math.sin(b/2),2)));
		s = s*EARTH_RADIUS; 
		s = Math.round(s*10000)/10000;
		return s; 
	}
	//将半角转换为全角
	public static String ToDBC(String input) {   
		char[] c = input.toCharArray();   
		for (int i = 0; i < c.length; i++) {   
			if (c[i] == 12288) {   
				c[i] = (char) 32;   
				continue;   
			}   
			if (c[i] > 65280 && c[i] < 65375)   
				c[i] = (char) (c[i] - 65248);   
		}   
		return new String(c);   
	}  
	
	public static final int FROMALBUM=0;//从手机图片库中获取照片的标志
	public static final int FROMCAMERA=1;//使用照相机拍照的标志
	
	//将得到的微博信息�?Sun Jul 15 19:13:16 +0800 2012"格式的时间串转换�?2012-7-15 19:13:16"的格�?
	public static String parse_Create_at(String create_at){
		StringBuffer sbuffer=new StringBuffer();
		String[] splitStr=create_at.split(" ");
		//----------打印----------
		/*for(String s:splitStr){
			System.out.println("create_at :"+s);
		}*/
		
		sbuffer.append(splitStr[5]+"-");//年份
		if(null!=getMonth(splitStr[1])){//月份
			sbuffer.append(getMonth(splitStr[1])+"-");
		}else{
			sbuffer.append(splitStr[1]+"-");
		}
		sbuffer.append(splitStr[2]+" ");//日期
		sbuffer.append(splitStr[3]);//时分�?
		
		return sbuffer.toString();
	}
	//通过给的英文�?��，返回是几月�?
	public static String getMonth(String month){
		String monthNum=null;
		if(month.equals("Jan")){//�?��January
			monthNum="1";
		}else if(month.equals("Feb")){//二月February
			monthNum="2";
		}else if(month.equals("Mar")){//三月March
			monthNum="3";
		}else if(month.equals("Apr")){//四月April
			monthNum="4";
		}else if(month.equals("May")){//五月May
			monthNum="5";
		}else if(month.equals("Jun")){//六月June
			monthNum="6";
		}else if(month.equals("Jul")){//七月July
			monthNum="7";
		}else if(month.equals("Aug")){//八月August
			monthNum="8";
		}else if(month.equals("Sep")){//九月September
			monthNum="9";
		}else if(month.equals("Oct")){//十月October
			monthNum="10";
		}else if(month.equals("Nov")){//十一月November
			monthNum="11";
		}else if(month.equals("Dec")){//十二月December
			monthNum="12";
		}
		return monthNum;
	}
	
	
}
