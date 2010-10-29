package com.knifenomad.anonymous;

import java.util.HashMap;
import java.util.Iterator;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class Apis {
	
	public final static String BASEURL = "http://175.122.253.4:33333/cgi-bin/";
	
	public static JList getMyEventList(Context context, MapView view) {
		GeoPoint topleft     = view.getProjection().fromPixels(0, 0);
		GeoPoint bottomRight = view.getProjection().fromPixels(view.getWidth(), view.getHeight());
		
		JDict param = new JDict();
		param.set("method", "get");
		param.set("gid"            , Global.getDict("user").getString("gid"));
		param.set("topLeftLon"     , Double.toString(topleft.getLongitudeE6()/1E6));
		param.set("bottomRightLon" , Double.toString(bottomRight.getLongitudeE6()/1E6));
		param.set("topLeftLat"     , Double.toString(topleft.getLatitudeE6()/1E6));
		param.set("bottomRightLat" , Double.toString(bottomRight.getLatitudeE6()/1E6));
		
		String event_list = call(context, "event.py", param);
		JDict event_dict = JDict.decode(event_list);
		return event_dict.getList("rs");
	}
	
	public static JList getMyGroupList(Context context) {
		JDict myself = Global.getDict("user");
		JDict params = new JDict();
		params.put("method" , "list");
		params.put("email"  , myself.getString("email"));
		params.put("pending", "0");
		params.put("owner"  , "2");
		String group_list = call(context, "group.py", params);
		
		JDict group_dict = JDict.decode(group_list);
		return group_dict.getList("rs");
	}
	
	public static String call(Context context, String url, JDict params) {
		ProgressDialog dialog = Global.showLoading(context);
		Iterator it = params.keys();
		
		if (it.hasNext()) url += "?";
		
		while(it.hasNext()) {	
			String key = (String) it.next();
			String val = (String) params.get(key);	
			url += String.format("%s=%s&", key, val);
		}
		HttpData data = HttpRequest.get(BASEURL + url);
		dialog.dismiss();
		return data.content;
	}
	
	public static String call(Context context, String url, String json) {
		JDict params = JDict.decode(json);
		return call(context, url, params);
	}
}
