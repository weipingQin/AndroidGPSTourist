package com.qinweiping.toutistassistant.utils;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.touristassistant.R;

  	public class LocationOverlay extends MyLocationOverlay{

  		public LocationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		/*@Override
  		protected boolean dispatchTap() {
  				View view = getLayoutInflater().inflate(R.layout.custom_text_view, null);
  				TextView text_title = (TextView) view.findViewById(R.id.marker_title);
  				TextView text_text = (TextView) view.findViewById(R.id.marker_text);
				SpannableString titleText = new SpannableString(temLocation.getCity());
  				titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
  						titleText.length(), 0);
  				text_title.setText(titleText);
  				SpannableString texttext = new SpannableString(temLocation.getAddrStr());
  				texttext.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
  						texttext.length(), 0);
  				text_text.setText(texttext);
  				pop.showPopup(BMapUtil.getBitmapFromView(view),
					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
					8);
  			return true;
  		}*/
  		
  	}