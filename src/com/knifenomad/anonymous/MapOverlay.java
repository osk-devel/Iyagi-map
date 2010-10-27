package com.knifenomad.anonymous;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import android.content.Context;
import java.util.Iterator;

public class MapOverlay extends ItemizedOverlay<OverlayItem> {
//{'email':x[0], 'nickname':nick, 'msg':msg, 'latitude':x[3], 'longitude':x[4], 'ctime':int(x[5]), 'eid':int(x[6]), 'picture':int(x[7])}
	private ArrayList<OverlayItem> mOverlays;
	private Context mContext;
	
	public MapOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		mOverlays = new ArrayList<OverlayItem>();
		// TODO Auto-generated constructor stub
	}
	
	public MapOverlay(Drawable defaultMarker, Context context) {
		  super(defaultMarker);
		  mContext = context;
	}
	
    public void addOverlay(OverlayItem overlay)
    {
        mOverlays.add(overlay);
        populate();
    }
    
	public void addEventList(JList list) {
		for(int i=0; i< list.length(); i++) {
			addEvent(list.getDict(i));
		}
	}
	
	public void addEvent(Double lat, Double lng, String msg, String title) {
		
		GeoPoint point = new GeoPoint(
                (int) (lat * 1E6), 
                (int) (lng * 1E6));
		
		OverlayItem item = new OverlayItem(point, title, msg);
		addOverlay(item);
	}
	
	public void addEvent(Double lat, Double lng, String msg, String title, int imgsrc) {
		
		GeoPoint point = new GeoPoint(
                (int) (lat * 1E6), 
                (int) (lng * 1E6));
		
		OverlayItem item = new OverlayItem(point, title, msg);
		Drawable flag = mContext.getResources().getDrawable(imgsrc);
		flag.setBounds(0, 0, flag.getIntrinsicWidth(), flag.getIntrinsicHeight());
		item.setMarker(flag);
		addOverlay(item);
	}
	
	public void addEvent(JDict dict) {
		double lat	 = Double.parseDouble(dict.getString("latitude"));
		double lng	 = Double.parseDouble(dict.getString("longitude"));
		String msg	 = dict.getString("msg");
		String title = dict.getString("nickname");
		addEvent(lat, lng, msg, title, android.R.drawable.presence_offline);
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  Global.alert(mContext, item.getTitle(), item.getSnippet());
	  return true;
	}
}
