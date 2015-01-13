package com.qinweiping.toutistassistant.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BaiduMapUtil {

	private static final String AK = "B01208ce9e1449527bd95e8ab9a018c5";
	
	/**
	 * 閫氳繃鍦板悕杩斿洖缁忕含搴�
	 * 
	 * @param address
	 *            浼犲叆鐨勫湴鍚�
	 *            
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, String> getPosition(String address)
			throws IOException {

		// 灏嗗湴鍧�浆鎹㈡垚utf-8鐨勫崄鍏繘鍒�
		address = URLEncoder.encode(address, "UTF-8");

		URL url = new URL("http://api.map.baidu.com/geocoder?address="
				+ address + "&output=json&key=" + AK);
		
		 BufferedReader in = new BufferedReader(
                 new InputStreamReader(url.openStream()));
		 String res;
		 StringBuilder sb = new StringBuilder("");
		 while((res = in.readLine())!=null){
		 sb.append(res.trim());
		 }
	    in.close();
	    String str = sb.toString();
	    
//	    //杈撳嚭鐪嬬湅
//	    System.out.println(address + "==================!!!!!" + str);
	    
	    //鑾峰緱缁忕含搴�
	    Map<String,String> map = null;
	    if(null != str && !"".equals(str.trim()) && str.length() >= 0){
	    	
	    	 int lngStart = str.indexOf("lng\":");
	    	 int lngEnd = str.indexOf(",\"lat");
	    	 int latEnd = str.indexOf("},\"precise");
	    	 if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
	    	     String lng = str.substring(lngStart+5, lngEnd);
	    	     String lat = str.substring(lngEnd+7, latEnd);
	    	     map = new HashMap<String,String>();
	    	     map.put("lng", lng);
	    	     map.put("lat", lat);
	    	     return map;
	    	 }
	    }
	    
	    return null;
	    
	}

	/**
	 * 閫氳繃鍦板悕杩斿洖缁忕含搴�
	 * 
	 * @param address
	 *            浼犲叆鐨勫湴鍚�
	 *            
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, String> getPosition(String city, String address)
			throws IOException {

		// 灏嗗湴鍧�浆鎹㈡垚utf-8鐨勫崄鍏繘鍒�
		address = URLEncoder.encode(address, "UTF-8");

		URL url = new URL("http://api.map.baidu.com/geocoder?address="
				+ address + "&City=" + city + "&output=json&key=" + AK);
		
		 BufferedReader in = new BufferedReader(
                 new InputStreamReader(url.openStream()));
		 String res;
		 StringBuilder sb = new StringBuilder("");
		 while((res = in.readLine())!=null){
		 sb.append(res.trim());
		 }
	    in.close();
	    String str = sb.toString();

	    
	    //鑾峰緱缁忕含搴�
	    Map<String,String> map = null;
	    if(null != str && !"".equals(str.trim()) && str.length() >= 0){
	    	
	    	 int lngStart = str.indexOf("lng\":");
	    	 int lngEnd = str.indexOf(",\"lat");
	    	 int latEnd = str.indexOf("},\"precise");
	    	 if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
	    	     String lng = str.substring(lngStart+5, lngEnd);
	    	     String lat = str.substring(lngEnd+7, latEnd);
	    	     map = new HashMap<String,String>();
	    	     map.put("lng", lng);
	    	     map.put("lat", lat);
	    	     return map;
	    	 }
	    }
	    
	    return null;
	    
	}
}
